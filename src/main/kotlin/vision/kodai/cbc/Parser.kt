package vision.kodai.cbc

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first

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
suspend fun Flow<Token>.parse(): Entity.Expr<Int> {
    val (expr, rem) = expr()
    if (rem.count() != 0) throw ParseException(ParseExnReason.UnexpectedNonEOF)
    return expr
}

suspend fun <T> Flow<T>.firstAndTail(): Pair<T, Flow<T>> = Pair(first(), drop(1))

fun <A1, A2, B> Pair<A1, Flow<B>>.mapFirst(f: (A1) -> A2): Pair<A2, Flow<B>> =
    Pair(f(first), second)

/** 式のパーサ */
@Throws(ParseException::class)
suspend fun Flow<Token>.expr() =
    intLiteral().run {
        val lhs = first
        second.firstAndTail().run {
            val op = first
            if (op !is Token.Plus) throw ParseException(ParseExnReason.UnexpectedToken("+", op))
            second.intLiteral().mapFirst { rhs -> Entity.Expr.Add(lhs, rhs, lhs.begin, rhs.end) }
        }
    }

/** 整数リテラルのパーサ */
@Throws(ParseException::class)
suspend fun Flow<Token>.intLiteral() =
    try {
        firstAndTail()
    } catch (e: NoSuchElementException) {
        throw ParseException(ParseExnReason.UnexpectedEOF("int"))
    }.mapFirst { token ->
        if (token !is Token.IntToken)
            throw ParseException(ParseExnReason.UnexpectedToken("int", token))
        Entity.Expr.IntLiteral(token.raw, token.begin, token.end)
    }
