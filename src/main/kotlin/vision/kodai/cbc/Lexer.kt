package vision.kodai.cbc

import kotlinx.coroutines.flow.flow
import vision.kodai.cbc.token.IntToken
import java.io.BufferedReader

/** 単項演算子 */
enum class UnaryOp { PLUS, MINUS }

/** 字句解析器の状態 */
sealed interface LexerState {
    /** 初期状態 */
    object Initial : LexerState

    /** 単項演算子を直前に読み取っており、数字を必要としている状態 */
    data class ShouldReceiveDigit(val unaryOp: UnaryOp) : LexerState

    /** 整数リテラルを読み取っている途中であり、追加で数字を読み取れる状態 */
    data class CanReceiveAdditionalDigit(
        val unaryOp: UnaryOp,
        val digits: String
    ) : LexerState
}

/** 字句解析の失敗 */
class LexerException(msg: String) : Exception(msg)

/** 字句解析を行う */
fun lex(reader: BufferedReader) = flow {
    val (state, numChars) =
        reader
            .lineSequence()
            .flatMap { it.toList() }
            .runningFold<Char, Pair<LexerState, Int>>(Pair(LexerState.Initial, -1)) { (prevState, numChars), c ->
                val state = when (prevState) {
                    is LexerState.Initial -> when {
                        c == '+' -> LexerState.ShouldReceiveDigit(UnaryOp.PLUS)
                        c == '-' -> LexerState.ShouldReceiveDigit(UnaryOp.MINUS)
                        c.isDigit() -> LexerState.CanReceiveAdditionalDigit(UnaryOp.PLUS, c.toString())
                        else -> throw LexerException("Expected +, - or digit")
                    }
                    is LexerState.ShouldReceiveDigit -> when {
                        c.isDigit() -> LexerState.CanReceiveAdditionalDigit(prevState.unaryOp, c.toString())
                        else -> throw LexerException("Expected digit")
                    }
                    is LexerState.CanReceiveAdditionalDigit -> when {
                        c.isDigit() -> LexerState.CanReceiveAdditionalDigit(prevState.unaryOp, prevState.digits + c)
                        else -> throw LexerException("Expected digit")
                    }
                }
                Pair(state, numChars + 1)
            }
            .last()

    when (state) {
        is LexerState.CanReceiveAdditionalDigit -> {
            val begin = Point(0, 0)
            val end = Point(0, numChars)
            val intVal = state.digits.toInt() * if (state.unaryOp == UnaryOp.MINUS) {
                -1
            } else {
                1
            }
            emit(IntToken(begin, end, intVal))
        }
        else -> throw LexerException("Non-accepting state")
    }
}
