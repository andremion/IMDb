package com.andremion.imdb.data.mapper

import com.andremion.imdb.data.local.entity.MovieDetailsEntity
import com.andremion.imdb.data.local.entity.MovieEntity
import com.andremion.imdb.domain.Movie
import org.junit.Test
import kotlin.test.assertEquals

class MoviesDomainMapperTest {

    private val sut: MoviesDomainMapper =
        MoviesDomainMapper()

    @Test
    fun `map movies`() {
        val entities = aListOfEntities()

        val actual = sut.map(entities)

        val expected = aListOfMovies()
        assertEquals(expected, actual)
    }

    @Test
    fun `map details`() {
        val movie = aMovieEntity()
        val details = aMovieDetailsEntity()

        val actual = sut.map(movie, details)

        val expected = aDetailedMovie()
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
    listOf(aMovie())

private fun aMovie(): Movie =
    Movie(
        id = "id",
        image = "image",
        title = "title",
        year = 2021,
        details = null
    )

private fun aMovieEntity(): MovieEntity =
    MovieEntity(
        id = "id",
        image = "image",
        title = "title",
        year = 2021,
    )

private fun aMovieDetailsEntity(): MovieDetailsEntity =
    MovieDetailsEntity(
        movieId = "movieId",
        rating = 7.5F,
        runtime = 90,
        outline = "outline",
        summary = "summary",
        genres = listOf("genre"),
    )

private fun aDetailedMovie(): Movie =
    Movie(
        id = "id",
        image = "image",
        title = "title",
        year = 2021,
        details = Movie.Details(
            rating = 7.5f,
            runtime = 90,
            outline = "outline",
            summary = "summary",
            genres = listOf("genre")
        )
    )
