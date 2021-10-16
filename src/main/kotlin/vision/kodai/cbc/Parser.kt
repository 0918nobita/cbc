package vision.kodai.cbc

import kotlinx.coroutines.flow.*

/** 構文解析に失敗した原因 */
sealed interface ParseExnReason {
    object UnexpectedNonEOF : ParseExnReason
    data class UnexpectedToken(val expected: String, val actual: Token): ParseExnReason
    data class UnexpectedEOF(val expected: String): ParseExnReason
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

/** 式のパーサ */
@Throws(ParseException::class)
suspend fun Flow<Token>.expr(): Pair<Entity.Expr<Int>, Flow<Token>> {
    val (lhs, rem1) = intLiteral()
    if (rem1.count() == 0) return Pair(lhs, rem1)

    val op = rem1.first()
    if (op !is Token.Plus)
        throw ParseException(ParseExnReason.UnexpectedToken("+", op))

    val (rhs, rem2) = rem1.drop(1).intLiteral()

    return Pair(Entity.Expr.Add(lhs, rhs, lhs.begin, rhs.end), rem2)
}

/** 整数リテラルのパーサ */
@Throws(ParseException::class)
suspend fun Flow<Token>.intLiteral(): Pair<Entity.Expr.IntLiteral, Flow<Token>> {
    val token = try {
        first()
    } catch (e: NoSuchElementException) {
        throw ParseException(ParseExnReason.UnexpectedEOF("int"))
    }

    if (token !is Token.IntToken)
        throw ParseException(ParseExnReason.UnexpectedToken("int", token))

    return Pair(Entity.Expr.IntLiteral(token.raw, token.begin, token.end), drop(1))
}
