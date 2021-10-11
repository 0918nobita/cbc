package vision.kodai.cbc.ast

import vision.kodai.cbc.Location

// TODO: porting
data class Ast(
    val source: Location,
    val decls: Decls
)
