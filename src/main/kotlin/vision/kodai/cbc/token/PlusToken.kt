package vision.kodai.cbc.token

import vision.kodai.cbc.Point

data class PlusToken(
    override val begin: Point,
    override val end: Point
) : Token
