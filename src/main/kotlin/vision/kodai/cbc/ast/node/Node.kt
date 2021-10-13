package vision.kodai.cbc.ast.node

import vision.kodai.cbc.Dumpable
import vision.kodai.cbc.Location

// TODO: porting
sealed interface Node : Dumpable {
    val location: Location

    sealed interface Stmt : Node

    sealed interface Expr : Node
}
