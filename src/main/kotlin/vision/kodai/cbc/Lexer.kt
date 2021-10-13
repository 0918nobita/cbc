package vision.kodai.cbc

import kotlinx.coroutines.flow.flow
import vision.kodai.cbc.token.IntToken
import java.io.BufferedReader

enum class UnaryOp { PLUS, MINUS }

sealed interface LexerState {
    object Initial : LexerState

    data class ShouldReceiveDigit(val unaryOp: UnaryOp) : LexerState

    data class CanReceiveAdditionalDigit(
        val unaryOp: UnaryOp,
        val digits: String
    ) : LexerState
}

class LexerException(msg: String) : Exception(msg)

class Lexer(reader: BufferedReader) {
    val tokens = flow {
        val chars = reader.lineSequence().flatMap { it.toList() }
        var state: LexerState = LexerState.Initial
        var numChars = -1

        for (c in chars) {
            state = when (state) {
                is LexerState.Initial -> when {
                    c == '+' -> LexerState.ShouldReceiveDigit(UnaryOp.PLUS)
                    c == '-' -> LexerState.ShouldReceiveDigit(UnaryOp.MINUS)
                    c.isDigit() -> LexerState.CanReceiveAdditionalDigit(UnaryOp.PLUS, c.toString())
                    else -> throw LexerException("Expected +, - or digit")
                }
                is LexerState.ShouldReceiveDigit -> when {
                    c.isDigit() -> LexerState.CanReceiveAdditionalDigit(state.unaryOp, c.toString())
                    else -> throw LexerException("Expected digit")
                }
                is LexerState.CanReceiveAdditionalDigit -> when {
                    c.isDigit() -> LexerState.CanReceiveAdditionalDigit(state.unaryOp, state.digits + c)
                    else -> throw LexerException("Expected digit")
                }
            }
            numChars++
        }

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
}
