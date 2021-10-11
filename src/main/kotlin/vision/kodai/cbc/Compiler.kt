package vision.kodai.cbc

import com.github.michaelbull.result.*
import vision.kodai.cbc.ast.Ast
import vision.kodai.cbc.ast.Decls
import kotlin.system.exitProcess

object Compiler {
    private const val PROGRAM_NAME = "cbc"
    private const val VERSION = "1.0.0"

    private val errorHandler = ErrorHandler(PROGRAM_NAME)

    private fun checkSyntax(): Boolean {
        // TODO: porting
        return true
    }

    private fun parseFile(@Suppress("UNUSED_PARAMETER") src: SourceFile): Ast {
        // TODO: porting
        return Ast(
            Location(
                "example",
                CflatToken(0, 0, 0, 0, "")
            ),
            Decls()
        )
    }

    private fun compile(src: SourceFile): Result<Unit, CompileError> {
        // TODO: porting
        @Suppress("UNUSED_VARIABLE")
        val ast = parseFile(src)
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
