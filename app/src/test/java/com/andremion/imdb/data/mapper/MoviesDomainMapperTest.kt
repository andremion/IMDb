package com.andremion.imdb.data.mapper

import com.andremion.imdb.data.local.entity.MovieEntity
import com.andremion.imdb.domain.Movie
import org.junit.Test
import kotlin.test.assertEquals

class MoviesDomainMapperTest {

    private val sut: MoviesDomainMapper =
        MoviesDomainMapper()

    @Test
    fun map() {
        val entities = aListOfEntities()

        val actual = sut.map(entities)

        val expected = aListOfMovies()
        assertEquals(expected, actual)
    }
}

private fun aListOfEntities(): List<MovieEntity> =
    listOf(
        MovieEntity(
            id = "id",
            image = "image",
            title = "title",
            year = 2021,
        )
    )

private fun aListOfMovies(): List<Movie> =
    listOf(
        Movie(
            id = "id",
            image = "image",
            title = "title",
            year = 2021,
            details = null
        )
    )
