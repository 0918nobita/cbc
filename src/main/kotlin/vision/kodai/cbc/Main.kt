package vision.kodai.cbc

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    Compiler.main(args)

    runBlocking {
        "42"
            .asSequence()
            .asFlow()
            .lex()
            .catch {
                System.err.println(it.message)
                exitProcess(1)
            }.collect {
                println(it)
            }
    }
}
