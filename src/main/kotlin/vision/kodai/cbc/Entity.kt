package vision.kodai.cbc

sealed interface Entity {
    val begin: Point?
    val end: Point?

    sealed interface Expr<out T> : Entity {
        fun eval(): T

        data class Add(
            val lhs: Expr<Int>,
            val rhs: Expr<Int>,
            override val begin: Point? = lhs.begin,
            override val end: Point? = rhs.end
        ) : Expr<Int> {
            override fun eval() = lhs.eval() + rhs.eval()
        }

        data class Sub(
            val lhs: Expr<Int>,
            val rhs: Expr<Int>,
            override val begin: Point? = lhs.begin,
            override val end: Point? = rhs.end
        ) : Expr<Int> {
            override fun eval() = lhs.eval() - rhs.eval()
        }

        data class Mul(
            val lhs: Expr<Int>,
            val rhs: Expr<Int>,
            override val begin: Point? = lhs.begin,
            override val end: Point? = rhs.end
        ) : Expr<Int> {
            override fun eval() = lhs.eval() * rhs.eval()
        }

        data class Div(
            val lhs: Expr<Int>,
            val rhs: Expr<Int>,
            override val begin: Point? = lhs.begin,
            override val end: Point? = rhs.end
        ) : Expr<Int> {
            override fun eval() = lhs.eval() / rhs.eval()
        }

        data class IntLiteral(
            val raw: Int,
            override val begin: Point? = null,
            override val end: Point? = null
        ) : Expr<Int> {
            override fun eval() = raw
        }
    }
}
