package vision.kodai.cbc

interface Dumpable {
    fun dump(dumper: Dumper)
}

class Dumper {
    @Suppress("UNUSED_PARAMETER")
    fun printMember(name: String, member: Dumpable) {
        // TODO: porting
    }

    @Suppress("UNUSED_PARAMETER")
    fun printNodeList(name: String, list: List<Dumpable>) {
        // TODO: porting
    }
}
