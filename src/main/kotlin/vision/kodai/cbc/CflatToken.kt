package vision.kodai.cbc

data class CflatToken(
    val beginLine: Int,
    val beginColumn: Int,
    val endLine: Int,
    val endColumn: Int,
    val raw: String
)
