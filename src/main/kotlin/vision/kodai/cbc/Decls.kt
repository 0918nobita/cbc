package vision.kodai.cbc

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
)