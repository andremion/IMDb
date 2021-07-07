package com.andremion.imdb.data.mapper

import com.andremion.imdb.data.local.entity.MovieDetailsEntity
import com.andremion.imdb.data.local.entity.MovieEntity
import com.andremion.imdb.data.remote.dto.MovieDetailsDTO
import com.andremion.imdb.data.remote.dto.MovieOverviewDTO
import org.junit.Test
import kotlin.test.assertEquals

class MoviesLocalMapperTest {

    private val sut: MoviesLocalMapper =
        MoviesLocalMapper()

    @Test
    fun `map details`() {
        val dto = aMovieDetailsDTO()

        val actual = sut.map(dto)

        val expected = aMovieEntity()
        assertEquals(expected, actual)
    }

    @Test
    fun `map overview`() {
        val movieId = "1"
        val dto = aMovieOverviewDTO()

        val actual = sut.map(movieId, dto)

        val expected = aMovieDetailsEntity(movieId)
        assertEquals(expected, actual)
    }
}

private fun aMovieDetailsDTO(): MovieDetailsDTO =
    MovieDetailsDTO(
        id = "id",
        image = "image",
        title = "title",
        year = 2021,
    )

private fun aMovieOverviewDTO(): MovieOverviewDTO =
    MovieOverviewDTO(
        ratings = MovieOverviewDTO.Ratings(rating = 7.5f),
        title = MovieOverviewDTO.Title(runningTimeInMinutes = 90),
        plotSummary = MovieOverviewDTO.Plot(text = "summary"),
        plotOutline = MovieOverviewDTO.Plot(text = "outline"),
        genres = listOf("genre"),
    )

private fun aMovieEntity(): MovieEntity =
    MovieEntity(
        id = "id",
        image = "image",
        title = "title",
        year = 2021,
    )

private fun aMovieDetailsEntity(movieId: String): MovieDetailsEntity =
    MovieDetailsEntity(
        movieId = movieId,
        rating = 7.5F,
        runtime = 90,
        outline = "outline",
        summary = "summary",
        genres = listOf("genre"),
    )
