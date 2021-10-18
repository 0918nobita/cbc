package vision.kodai.cbc

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first

typealias TokenFlow = Flow<Token>

typealias ParseResult<T> = Pair<T, TokenFlow>

/** 構文解析に失敗した原因 */
sealed interface ParseExnReason {
    object UnexpectedNonEOF : ParseExnReason
    data class UnexpectedToken(val expected: String, val actual: Token) : ParseExnReason
    data class UnexpectedEOF(val expected: String) : ParseExnReason
}

/** 構文解析の失敗 */
data class ParseException(val reason: ParseExnReason) : Exception()

/** プログラム全体のパーサ */
suspend fun TokenFlow.parse(): Entity.Expr<Int> {
    val (expr, rem) = term()
    if (rem.count() != 0) throw ParseException(ParseExnReason.UnexpectedNonEOF)
    return expr
}

/** 項のパーサ */
suspend fun TokenFlow.term(): ParseResult<Entity.Expr<Int>> {
    val res = intLiteral()
    val (lhs, rem) = res

    val op = try {
        rem.first()
    } catch (e: NoSuchElementException) {
        return res
    }

    return when (op) {
        is Token.Plus -> {
            val (rhs, rem2) = rem.drop(1).intLiteral()
            Pair(Entity.Expr.Add(lhs, rhs), rem2)
        }
        is Token.Minus -> {
            val (rhs, rem2) = rem.drop(1).intLiteral()
            Pair(Entity.Expr.Sub(lhs, rhs), rem2)
        }
        else -> res
    }
}

/** 整数リテラルのパーサ */
suspend fun TokenFlow.intLiteral(): ParseResult<Entity.Expr.IntLiteral> {
    val token = try {
        first()
    } catch (e: NoSuchElementException) {
        throw ParseException(ParseExnReason.UnexpectedEOF("int"))
    }

    if (token !is Token.IntToken)
        throw ParseException(ParseExnReason.UnexpectedToken("int", token))

    return Pair(Entity.Expr.IntLiteral(token.raw, token.begin, token.end), drop(1))
}
