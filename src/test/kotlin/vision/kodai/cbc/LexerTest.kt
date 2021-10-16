package vision.kodai.cbc

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList

class LexerTest : StringSpec({
    "tokenization" {
        val src = "3 + 4"
        val tokenFlow = src.toList().asFlow().lex()
        val expected =
            listOf(
                Token.IntToken(3, Point(0, 0), Point(0, 0)),
                Token.Plus(Point(0, 2), Point(0, 2)),
                Token.IntToken(4, Point(0, 4), Point(0, 4))
            )
        tokenFlow.toList() shouldBe expected
    }
})
