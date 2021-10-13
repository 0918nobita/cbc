package vision.kodai.cbc

import kotlinx.coroutines.flow.flow
import vision.kodai.cbc.token.IntToken
import java.io.BufferedReader

enum class UnaryOp { PLUS, MINUS }

sealed interface LexerState {
    object Initial : LexerState
}

data class ShouldReceiveDigit(val unaryOp: UnaryOp) : LexerState

data class CanReceiveAdditionalDigit(
    val unaryOp: UnaryOp,
    val digits: String
) : LexerState

class LexerException(msg: String) : Exception(msg)

class Lexer(reader: BufferedReader) {
    val tokens = flow {
        val (state, numChars) =
            reader
                .lineSequence()
                .flatMap { it.toList() }
                .runningFold<Char, Pair<LexerState, Int>>(Pair(LexerState.Initial, -1)) { (prevState, numChars), c ->
                    val state = when (prevState) {
                        is LexerState.Initial -> when {
                            c == '+' -> ShouldReceiveDigit(UnaryOp.PLUS)
                            c == '-' -> ShouldReceiveDigit(UnaryOp.MINUS)
                            c.isDigit() -> CanReceiveAdditionalDigit(UnaryOp.PLUS, c.toString())
                            else -> throw LexerException("Expected +, - or digit")
                        }
                        is ShouldReceiveDigit -> when {
                            c.isDigit() -> CanReceiveAdditionalDigit(prevState.unaryOp, c.toString())
                            else -> throw LexerException("Expected digit")
                        }
                        is CanReceiveAdditionalDigit -> when {
                            c.isDigit() -> CanReceiveAdditionalDigit(prevState.unaryOp, prevState.digits + c)
                            else -> throw LexerException("Expected digit")
                        }
                    }
                    Pair(state, numChars + 1)
                }
                .last()

        when (state) {
            is CanReceiveAdditionalDigit -> {
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
