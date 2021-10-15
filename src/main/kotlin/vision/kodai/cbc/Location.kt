package vision.kodai.cbc

data class Location(val sourceName: String, val token: Token) {
    override fun toString() = "$sourceName:${token.begin.line}"
}
