package vision.kodai.cbc.ast

import vision.kodai.cbc.Dumpable
import vision.kodai.cbc.Location

// TODO: porting
sealed interface Node : Dumpable {
    val location: Location

    // TODO: porting
    sealed interface Stmt : Node {
        sealed interface If : Stmt
    }

    // TODO: porting
    sealed interface Expr : Node {
        sealed interface Funcall : Expr
    }
}
