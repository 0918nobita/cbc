package vision.kodai.cbc

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import vision.kodai.cbc.token.IntToken

class LexerTest : StringSpec({
    "tokenization" {
        val src = "3 7\n    21\n"
        val tokenFlow = src.toList().asFlow().lex()
        val expected =
            listOf(
                IntToken(Point(0, 0), Point(0, 0), 3),
                IntToken(Point(0, 2), Point(0, 2), 7),
                IntToken(Point(1, 4), Point(1, 5), 21)
            )
        val tokens = tokenFlow.toList()
        expected shouldBe tokens
    }
})
