package vision.kodai.cbc

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import vision.kodai.cbc.token.IntToken
import vision.kodai.cbc.token.PlusToken

class LexerTest : StringSpec({
    "tokenization" {
        val src = "3 + 7"
        val tokenFlow = src.toList().asFlow().lex()
        val expected =
            listOf(
                IntToken(Point(0, 0), Point(0, 0), 3),
                PlusToken(Point(0, 2), Point(0, 2)),
                IntToken(Point(0, 4), Point(0, 4), 7)
            )
        tokenFlow.toList() shouldBe expected
    }
})
