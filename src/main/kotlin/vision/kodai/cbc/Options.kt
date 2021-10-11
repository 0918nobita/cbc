package vision.kodai.cbc

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result

object Options {
    var mode: CompilerMode = CompilerMode.Initial
    var sourceFiles: List<SourceFile> = emptyList()

    fun parse(args: Array<String>): Result<Unit, OptionParseError> {
        // TODO: porting
        return Ok(Unit)
    }
}
