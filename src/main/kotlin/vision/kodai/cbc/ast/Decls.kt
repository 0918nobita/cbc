package vision.kodai.cbc.ast

// TODO: porting

class VarDef
class VarDecl

class FuncDef
class FuncDecl

class ConstDef

class StructDef

class UnionDef

class TypeAliasDef

data class Decls(
    val varDefs: Set<VarDef> = emptySet(),
    val varDecls: Set<VarDecl> = emptySet(),

    val funcDefs: Set<FuncDef> = emptySet(),
    val funcDecls: Set<FuncDecl> = emptySet(),

    val constDefs: Set<ConstDef> = emptySet(),

    val structDefs: Set<StructDef> = emptySet(),

    val unionDefs: Set<UnionDef> = emptySet(),

    val typeAliasDefs: Set<TypeAliasDef> = emptySet()
) {
    operator fun plus(newDecls: Decls) =
        Decls(
            varDefs + newDecls.varDefs,
            varDecls + newDecls.varDecls,
            funcDefs + newDecls.funcDefs,
            funcDecls + newDecls.funcDecls,
            constDefs + newDecls.constDefs,
            structDefs + newDecls.structDefs,
            unionDefs + newDecls.unionDefs,
            typeAliasDefs + newDecls.typeAliasDefs
        )
}