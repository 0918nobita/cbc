package vision.kodai.cbc

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first

suspend fun <T> Flow<T>.isEmpty() = count() == 0

typealias TokenFlow = Flow<Token>

typealias ParseResult<T> = Pair<T, TokenFlow>

suspend fun TokenFlow.pickFirst(): ParseResult<Token> = Pair(first(), drop(1))

fun <A1, A2> ParseResult<A1>.mapResult(f: (A1) -> A2): ParseResult<A2> =
    Pair(f(first), second)

suspend fun <A1, A2> ParseResult<A1>.chain(
    f: suspend TokenFlow.(A1) -> ParseResult<A2>
): ParseResult<A2> =
    f(second, first)

suspend fun <T> ParseResult<T>.chainOptional(
    f: suspend TokenFlow.(T) -> ParseResult<T>
): ParseResult<T> =
    if (second.isEmpty()) { this } else { f(second, first) }

/** 構文解析に失敗した原因 */
sealed interface ParseExnReason {
    object UnexpectedNonEOF : ParseExnReason
    data class UnexpectedToken(val expected: String, val actual: Token) : ParseExnReason
    data class UnexpectedEOF(val expected: String) : ParseExnReason
}

/** 構文解析の失敗 */
data class ParseException(val reason: ParseExnReason) : Exception()

/** プログラム全体のパーサ */
@Throws(ParseException::class)
suspend fun TokenFlow.parse(): Entity.Expr<Int> {
    val (expr, rem) = expr()
    if (rem.count() != 0) throw ParseException(ParseExnReason.UnexpectedNonEOF)
    return expr
}

/** 式のパーサ */
@Throws(ParseException::class)
suspend fun TokenFlow.expr(): ParseResult<Entity.Expr<Int>> =
    intLiteral().chainOptional<Entity.Expr<Int>> { lhs ->
        pickFirst().chain { op ->
            if (op !is Token.Plus) throw ParseException(ParseExnReason.UnexpectedToken("+", op))
            intLiteral().mapResult { rhs -> Entity.Expr.Add(lhs, rhs, lhs.begin, rhs.end) }
        }
    }

/** 整数リテラルのパーサ */
@Throws(ParseException::class)
suspend fun TokenFlow.intLiteral(): ParseResult<Entity.Expr.IntLiteral> =
    try {
        pickFirst()
    } catch (e: NoSuchElementException) {
        throw ParseException(ParseExnReason.UnexpectedEOF("int"))
    }.mapResult { token ->
        if (token !is Token.IntToken)
            throw ParseException(ParseExnReason.UnexpectedToken("int", token))
        Entity.Expr.IntLiteral(token.raw, token.begin, token.end)
    }
