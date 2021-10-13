package vision.kodai.cbc

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import java.io.BufferedReader
import java.io.StringReader

fun main(args: Array<String>) {
    Compiler.main(args)

    val lexer = Lexer(BufferedReader(StringReader("-42")))
    runBlocking {
        lexer.tokens.collect {
            println(it)
        }
    }
}
