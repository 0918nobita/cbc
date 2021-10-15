package vision.kodai.cbc

sealed interface Token {
    val begin: Point
    val end: Point

    data class IntToken(
        override val begin: Point,
        override val end: Point,
        val raw: Int
    ) : Token

    data class Plus(
        override val begin: Point,
        override val end: Point
    ) : Token
}
