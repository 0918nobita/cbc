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
    data class CanReceiveAdditionalDigit(
        val begin: Point,
        val end: Point,
        val digits: String
    ) : LexerState
}

/** 字句解析の失敗 */
class LexerException(msg: String) : Exception(msg)

/** 字句解析を行う */
fun Flow<Char>.lex(): Flow<Token> = flow {
    var point = Point(0, 0)

    val finalState = fold<Char, LexerState>(LexerState.Initial) { prevState, c ->
        val state = when (prevState) {
            is LexerState.Initial -> when {
                c.isDigit() -> LexerState.CanReceiveAdditionalDigit(point, point, c.toString())
                c.isWhitespace() -> LexerState.Initial
                else -> throw LexerException("Expected digit or whitespace")
            }
            is LexerState.CanReceiveAdditionalDigit -> when {
                c.isDigit() -> LexerState.CanReceiveAdditionalDigit(prevState.begin, point, prevState.digits + c)
                c.isWhitespace() -> {
                    emit(IntToken(prevState.begin, prevState.end, prevState.digits.toInt()))
                    LexerState.Initial
                }
                else -> throw LexerException("Expected digit or whitespace")
            }
        }

        point = if (c == '\n') {
            Point(point.line + 1, 0)
        } else {
            Point(point.line, point.column + 1)
        }

        state
    }

    when (finalState) {
        is LexerState.Initial -> {}
        is LexerState.CanReceiveAdditionalDigit ->
            emit(IntToken(finalState.begin, finalState.end, finalState.digits.toInt()))
    }
}
