package vision.kodai.cbc.ast.node

import vision.kodai.cbc.Dumper

data class FuncallNode(
    val expr: Node.Expr,
    val args: List<Node.Expr>
) : Node.Expr {
    override val location = expr.location

    override fun dump(dumper: Dumper) {
        dumper.printMember("expr", expr)
        dumper.printNodeList("args", args)
    }
}
