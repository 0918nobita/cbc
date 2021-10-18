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

suspend fun <T> Flow<T>.isEmpty() = count() == 0

fun <A1, A2, B> Pair<A1, Flow<B>>.mapFirst(f: (A1) -> A2): Pair<A2, Flow<B>> =
    Pair(f(first), second)

/*
suspend fun <A1, A2, B> Pair<A1, Flow<B>>.chain(f: suspend (A1) -> Pair<A2, Flow<B>>): Pair<A2, Flow<B>> =
    if (second.isEmpty()) { f(first) }
*/

/** 式のパーサ */
@Throws(ParseException::class)
suspend fun Flow<Token>.expr(): Pair<Entity.Expr<Int>, Flow<Token>> {
    val (lhs, rem1) = intLiteral()
    if (rem1.isEmpty()) return Pair(lhs, rem1)

    val (op, rem2) = rem1.firstAndTail()
    if (op !is Token.Plus)
        throw ParseException(ParseExnReason.UnexpectedToken("+", op))

    return rem2.intLiteral().mapFirst { rhs -> Entity.Expr.Add(lhs, rhs, lhs.begin, rhs.end) }
    /*
    return intLiteral()
        .chain { lhs ->
            firstAndTail().chain { op ->
                if (op !is Token.Plus) throw ParseException(ParseExnReason.UnexpectedToken("+", op))
                intLiteral().mapFirst { rhs -> Entity.Expr.Add(lhs, rhs, lhs.begin, rhs.end) }
            }
        }
     */
}

/** 整数リテラルのパーサ */
@Throws(ParseException::class)
suspend fun Flow<Token>.intLiteral(): Pair<Entity.Expr.IntLiteral, Flow<Token>> {
    val (token, rem) = try {
        firstAndTail()
    } catch (e: NoSuchElementException) {
        throw ParseException(ParseExnReason.UnexpectedEOF("int"))
    }

    if (token !is Token.IntToken)
        throw ParseException(ParseExnReason.UnexpectedToken("int", token))

    return Pair(Entity.Expr.IntLiteral(token.raw, token.begin, token.end), rem)
}
