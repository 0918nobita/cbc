package vision.kodai.cbc

import com.github.michaelbull.result.*
import kotlin.system.exitProcess

object Compiler {
    private const val PROGRAM_NAME = "cbc"
    // private const val VERSION = "1.0.0"

    private val errorHandler = ErrorHandler(PROGRAM_NAME)

    private fun checkSyntax(): Boolean {
        // TODO: porting
        return true
    }

    private fun compile(@Suppress("UNUSED_PARAMETER") src: SourceFile): Result<Unit, CompileError> {
        // TODO: porting
        return Ok(Unit)
    }

    private fun assemble(@Suppress("UNUSED_PARAMETER") src: SourceFile): Result<Unit, CompileError> {
        // TODO: porting
        return Ok(Unit)
    }

    private fun link(): Result<Unit, CompileError> {
        // TODO: porting
        return Ok(Unit)
    }

    private fun build() =
        Options.sourceFiles
            .fold(Unit) { _, src ->
                compile(src)
                    .andThen { assemble(src) }
            }
            .andThen { link() }

    fun main(args: Array<String>) {
        Options.parse(args).onFailure {
            errorHandler.error(it.msg)
            errorHandler.error("Try \"cbc --help\" for usage")
            exitProcess(1)
        }
        if (Options.mode == CompilerMode.CheckSyntax) {
            exitProcess(
                if (checkSyntax()) {
                    0
                } else {
                    1
                }
            )
        }
        build().onFailure {
            errorHandler.error(it.msg)
            exitProcess(1)
        }
    }
}
