package vision.kodai.cbc

data class Location(val sourceName: String, val token: CflatToken) {
    override fun toString() = "$sourceName:${token.beginLine}"
}
