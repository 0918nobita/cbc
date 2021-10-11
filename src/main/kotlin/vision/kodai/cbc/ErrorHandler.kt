package vision.kodai.cbc

import java.io.PrintStream

data class ErrorHandler(
    val programId: String,
    val out: PrintStream = System.err
) {
    var numErrors: Long = 0
    var numWarns: Long = 0
    val errorOccurred: Boolean
        get() = this.numErrors > 0

    fun error(msg: String, loc: Location? = null) {
        out.println("${loc ?: programId}: $msg")
        numErrors++
    }

    fun warn(msg: String, loc: Location? = null) {
        out.println("${loc ?: programId}: $msg")
        numWarns++
    }
}
