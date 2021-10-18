package vision.kodai.cbc

sealed interface Token {
    val begin: Point
    val end: Point

    data class IntToken(
        val raw: Int,
        override val begin: Point,
        override val end: Point
    ) : Token

    data class Plus(val point: Point) : Token {
        override val begin = point
        override val end = point
    }

    data class Minus(val point: Point) : Token {
        override val begin = point
        override val end = point
    }

    data class Times(val point: Point) : Token {
        override val begin = point
        override val end = point
    }

    data class Div(val point: Point) : Token {
        override val begin = point
        override val end = point
    }
}
