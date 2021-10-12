package vision.kodai.cbc.ast.node

import vision.kodai.cbc.Dumper
import vision.kodai.cbc.Location
import vision.kodai.cbc.ast.VarDef

sealed interface BlockMember {
    data class VarDefsInBlock(val varDef: VarDef) : BlockMember
    data class StmtsInBlock(val stmt: Node.Stmt) : BlockMember
}

data class BlockNode(
    override val location: Location,
    val members: List<BlockMember>
) : Node.Stmt.Block {
    override fun dump(dumper: Dumper) {
        members.map {
            when (it) {
                is BlockMember.VarDefsInBlock -> it.varDef
                is BlockMember.StmtsInBlock -> it.stmt
            }
        }.let {
            dumper.printNodeList("members", it)
        }
    }
}
