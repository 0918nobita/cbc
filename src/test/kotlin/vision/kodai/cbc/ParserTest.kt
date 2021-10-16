package vision.kodai.cbc

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.asFlow

class ParserTest : StringSpec({
    "parsing" {
        val src = "3 + 4"
        val program = src.toList().asFlow().lex().parse()
        val expected =
            Entity.Expr.Add(
                Entity.Expr.IntLiteral(3, Point(0, 0), Point(0, 0)),
                Entity.Expr.IntLiteral(4, Point(0, 4), Point(0, 4)),
                Point(0, 0),
                Point(0, 4)
            )
        program shouldBe expected
    }
})
