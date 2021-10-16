package vision.kodai.cbc

sealed interface Token {
    val begin: Point
    val end: Point

    data class IntToken(
        val raw: Int,
        override val begin: Point,
        override val end: Point
    ) : Token

    data class Plus(
        override val begin: Point,
        override val end: Point
    ) : Token
}
