package vision.kodai.cbc.ast

import vision.kodai.cbc.Dumpable

// TODO: porting
sealed interface Node : Dumpable {
    // TODO: porting
    sealed interface Stmt : Node

    // TODO: porting
    sealed interface Expr : Node {
        sealed interface Funcall : Expr
    }
}
