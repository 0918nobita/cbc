package vision.kodai.cbc

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import java.io.BufferedReader
import java.io.StringReader
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    Compiler.main(args)

    val lexer = Lexer(BufferedReader(StringReader("-42")))
    runBlocking {
        lexer.tokens.catch {
            System.err.println(it.message)
            exitProcess(1)
        }.collect {
            println(it)
        }
    }
}
