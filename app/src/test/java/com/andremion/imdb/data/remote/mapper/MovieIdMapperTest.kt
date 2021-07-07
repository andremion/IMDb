package com.andremion.imdb.data.remote.mapper

import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@RunWith(TestParameterInjector::class)
class MovieIdMapperTest {

    private val sut: MovieIdMapper =
        MovieIdMapper()

    @Test
    fun `success map`(@TestParameter testCase: SuccessTestCase) {
        val actual = sut.map(testCase.given)

        assertEquals(testCase.expected, actual)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `error map`(@TestParameter testCase: ErrorTestCase) {
        sut.map(testCase.given)
    }

    enum class SuccessTestCase(val given: String, val expected: String) {
        _title_tt123456_(
            given = "/title/tt123456/",
            expected = "tt123456"
        ),
        title_tt123456(
            given = "title/tt123456",
            expected = "tt123456"
        ),
        _tt123456_(
            given = "/tt123456/",
            expected = "tt123456"
        ),
        _123456tt123456_(
            given = "/123456tt123456/",
            expected = "tt123456"
        ),
    }

    enum class ErrorTestCase(val given: String) {
        _title_TT123456_(
            given = "/title/TT123456/"
        ),
        _title_123456_(
            given = "/title/123456/"
        ),
        _123456(
            given = "/123456"
        ),
    }
}
