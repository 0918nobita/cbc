package vision.kodai.cbc

import vision.kodai.cbc.token.Token

data class Location(val sourceName: String, val token: Token) {
    override fun toString() = "$sourceName:${token.begin.line}"
}
