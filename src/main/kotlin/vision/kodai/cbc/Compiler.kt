package vision.kodai.cbc

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.onFailure
import vision.kodai.cbc.ast.Ast
import vision.kodai.cbc.ast.Decls
import kotlin.system.exitProcess

enum class CompilerMode {
    Initial,
    CheckSyntax
}

data class CompileError(val msg: String)

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
                Token.IntToken(42, Point(0, 0), Point(0, 1))
            ),
            Decls()
        )
    }

    @Suppress("UNUSED_VARIABLE")
    private fun compile(src: SourceFile): Result<Unit, CompileError> {
        // TODO: porting
        val ast = parseFile(src)
        val typeSpec = Options.platform.typeSpec
        // semanticAnalyze()
        // IR generation
        // Assembly generation
        // writeFile()
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
            .fold(Ok(Unit)) { _, src ->
                return compile(src)
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
