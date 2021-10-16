package vision.kodai.cbc

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result

data class OptionParseError(val msg: String)

object Options {
    val platform = Platform.X64

    var mode: CompilerMode = CompilerMode.Initial
    var sourceFiles: List<SourceFile> = emptyList()

    fun parse(
        @Suppress("UNUSED_PARAMETER") args: Array<String>
    ): Result<Unit, OptionParseError> {
        // TODO: porting
        return Ok(Unit)
    }
}
