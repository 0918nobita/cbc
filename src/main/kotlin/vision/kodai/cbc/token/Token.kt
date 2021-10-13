package vision.kodai.cbc.token

import vision.kodai.cbc.Point

sealed interface Token {
    val begin: Point
    val end: Point
}
