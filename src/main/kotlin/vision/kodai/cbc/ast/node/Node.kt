package vision.kodai.cbc.ast.node

import vision.kodai.cbc.Dumpable
import vision.kodai.cbc.Location

// TODO: porting
sealed interface Node : Dumpable {
    val location: Location

    sealed interface Stmt : Node {
        sealed interface Block : Stmt
        sealed interface If : Stmt
    }

    sealed interface Expr : Node {
        sealed interface Funcall : Expr
    }
}
