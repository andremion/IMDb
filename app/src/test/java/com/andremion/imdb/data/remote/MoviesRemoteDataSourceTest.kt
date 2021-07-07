package com.andremion.imdb.data.remote

import com.andremion.imdb.data.remote.dto.MovieDetailsDTO
import com.andremion.imdb.data.remote.dto.MovieOverviewDTO
import com.andremion.imdb.data.remote.mapper.MovieIdMapper
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import kotlin.test.assertEquals

class MoviesRemoteDataSourceTest {

    private val mockService: ImdbService = mock()
    private val mockMovieIdMapper: MovieIdMapper = mock()

    private val sut: MoviesRemoteDataSource =
        MoviesRemoteDataSource(mockService, mockMovieIdMapper)

    @Test
    fun `get most popular movie ids`() = runBlockingTest {
        val expected = listOf("1", "2", "3")
        whenever(mockService.getMostPopularMovies()).thenReturn(expected)

        val actual = sut.getMostPopularMovieIds()

        assertEquals(expected, actual)
    }

    @Test
    fun `get details`() = runBlockingTest {
        val movieId = "movieId"
        val expected = aMovieDetails()
        whenever(mockService.getDetails(movieId)).thenReturn(expected)
        whenever(mockMovieIdMapper.map(movieId)).thenReturn(movieId)

        val actual = sut.getDetails(movieId)

        assertEquals(expected, actual)
    }

    @Test
    fun `get overview details`() = runBlockingTest {
        val movieId = "movieId"
        val expected = aMovieOverview()
        whenever(mockService.getOverviewDetails(movieId)).thenReturn(expected)
        whenever(mockMovieIdMapper.map(movieId)).thenReturn(movieId)

        val actual = sut.getOverviewDetails(movieId)

        assertEquals(expected, actual)
    }

}

private fun aMovieDetails(): MovieDetailsDTO =
    MovieDetailsDTO(
        id = "id",
        image = MovieDetailsDTO.Image(url = "image"),
        title = "title",
        year = 2021,
    )

private fun aMovieOverview(): MovieOverviewDTO =
    MovieOverviewDTO(
        ratings = MovieOverviewDTO.Ratings(rating = 7.5f),
        title = MovieOverviewDTO.Title(runningTimeInMinutes = 90),
        plotSummary = MovieOverviewDTO.Plot(text = "text"),
        plotOutline = MovieOverviewDTO.Plot(text = "text"),
        genres = listOf("genre"),
    )
