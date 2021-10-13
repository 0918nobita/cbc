package vision.kodai.cbc

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.fold
import vision.kodai.cbc.token.IntToken
import vision.kodai.cbc.token.Token

/** 字句解析器の状態 */
sealed interface LexerState {
    /** 数字を必要としている初期状態 */
    object Initial : LexerState

    /** すでに1つ以上数字を読み取っており、追加で数字を読み取れる状態 */
    data class CanReceiveAdditionalDigit(val digits: String) : LexerState
}

/** 字句解析の失敗 */
class LexerException(msg: String) : Exception(msg)

private fun reducer(pair: Pair<LexerState, Int>, c: Char): Pair<LexerState, Int> {
    val (prevState, numChars) = pair
    val state = when (prevState) {
        is LexerState.Initial -> when {
            c.isDigit() -> LexerState.CanReceiveAdditionalDigit(c.toString())
            else -> throw LexerException("Expected digit")
        }
        is LexerState.CanReceiveAdditionalDigit -> when {
            c.isDigit() -> LexerState.CanReceiveAdditionalDigit(prevState.digits + c)
            else -> throw LexerException("Expected digit")
        }
    }
    return Pair(state, numChars + 1)
}

/** 字句解析を行う */
fun Flow<Char>.lex(): Flow<Token> = flow {
    val (state, numChars) = fold(Pair(LexerState.Initial, -1), ::reducer)

    when (state) {
        is LexerState.CanReceiveAdditionalDigit -> {
            val begin = Point(0, 0)
            val end = Point(0, numChars)
            val intVal = state.digits.toInt()
            emit(IntToken(begin, end, intVal))
        }
        else -> throw LexerException("Non-accepting state")
    }
}
