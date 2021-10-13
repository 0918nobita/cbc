package vision.kodai.cbc

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    Compiler.main(args)

    val src = "3 7\n    21\n"
    val tokens = src.toList().asFlow().lex()

    runBlocking {
        tokens.catch {
            System.err.println(it.message)
            exitProcess(1)
        }.collect {
            println(it)
        }
    }
}
