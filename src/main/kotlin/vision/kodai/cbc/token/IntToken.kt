package vision.kodai.cbc.token

import vision.kodai.cbc.Point

data class IntToken(
    override val begin: Point,
    override val end: Point,
    val raw: Int
) : Token
