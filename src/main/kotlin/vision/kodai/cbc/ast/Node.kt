package vision.kodai.cbc.ast

import vision.kodai.cbc.Dumpable

// TODO: porting
sealed interface Node: Dumpable

// TODO: porting
sealed interface StmtNode : Node

// TODO: porting
sealed interface ExprNode : Node
