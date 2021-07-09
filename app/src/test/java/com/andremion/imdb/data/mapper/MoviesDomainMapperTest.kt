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
    fun `map movie id`() {
        val movieId = "1"

        val actual = sut.map(movieId)

        val expected = aMovie(movieId)
        assertEquals(expected, actual)
    }

    @Test
    fun `map movie`() {
        val movieId = "1"
        val movie = aMovieEntity(movieId)

        val actual = sut.map(movie)

        val expected = movieDetails(movieId)
        assertEquals(expected, actual)
    }

    @Test
    fun `map details`() {
        val movieId = "1"
        val movie = aMovieEntity(movieId)
        val details = aMovieDetailsEntity(movieId)

        val actual = sut.map(movie, details)

        val expected = aMovieOverview(movieId)
        assertEquals(expected, actual)
    }
}

private fun aMovieEntity(id: String): MovieEntity =
    MovieEntity(
        id = id,
        image = "image",
        title = "title",
        year = 2021,
    )

private fun aMovieDetailsEntity(id: String): MovieDetailsEntity =
    MovieDetailsEntity(
        movieId = id,
        rating = 7.5F,
        runtime = 90,
        outline = "outline",
        summary = "summary",
        genres = listOf("genre"),
    )

private fun aMovie(id: String): Movie =
    Movie(
        id = id,
        details = null
    )

private fun movieDetails(id: String): Movie =
    Movie(
        id = id,
        details = Movie.Details(
            image = "image",
            title = "title",
            year = 2021,
            overview = null
        )
    )

private fun aMovieOverview(id: String): Movie =
    Movie(
        id = id,
        details = Movie.Details(
            image = "image",
            title = "title",
            year = 2021,
            overview = Movie.Overview(
                rating = 7.5f,
                runtime = 90,
                outline = "outline",
                summary = "summary",
                genres = listOf("genre")
            )
        )
    )
