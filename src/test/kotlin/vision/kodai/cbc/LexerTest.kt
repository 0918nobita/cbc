package vision.kodai.cbc

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test
import vision.kodai.cbc.token.IntToken
import kotlin.test.assertEquals

object LexerTest {
    @Test
    @kotlinx.coroutines.ExperimentalCoroutinesApi
    fun tokenization() {
        val src = "3 7\n    21\n"
        val tokenFlow = src.toList().asFlow().lex()
        val expected =
            listOf(
                IntToken(Point(0, 0), Point(0, 0), 3),
                IntToken(Point(0, 2), Point(0, 2), 7),
                IntToken(Point(1, 4), Point(1, 5), 21)
            )
        runBlockingTest {
            val tokens = tokenFlow.toList()
            assertEquals(expected, tokens)
        }
    }
}
