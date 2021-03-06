package vision.kodai.cbc.ast.node

import vision.kodai.cbc.Dumper
import vision.kodai.cbc.Location

data class IfNode(
    override val location: Location,
    val cond: Node.Expr,
    val thenClause: Node.Stmt,
    val elseClause: Node.Stmt
) : Node.Stmt {
    override fun dump(dumper: Dumper) {
        dumper.printMember("cond", cond)
        dumper.printMember("thenClause", thenClause)
        dumper.printMember("elseClause", elseClause)
    }
}
