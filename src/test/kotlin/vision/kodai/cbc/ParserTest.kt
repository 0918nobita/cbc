package vision.kodai.cbc

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.asFlow

class ParserTest : StringSpec({
    "int literal" {
        val src = "12"
        val parseResult = src.toList().asFlow().lex().parse()
        val expected = Entity.Expr.IntLiteral(12, Point(0, 0), Point(0, 1))
        parseResult shouldBe expected
    }

    "addition" {
        val src = "3 + 4"
        val parseResult = src.toList().asFlow().lex().parse()
        val expected =
            Entity.Expr.Add(
                Entity.Expr.IntLiteral(3, Point(0, 0), Point(0, 0)),
                Entity.Expr.IntLiteral(4, Point(0, 4), Point(0, 4)),
                Point(0, 0),
                Point(0, 4)
            )
        parseResult shouldBe expected
    }
})
