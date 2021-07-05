package com.andremion.imdb.data.local

import com.andremion.imdb.data.local.entity.MovieDetailsEntity
import com.andremion.imdb.data.local.entity.MovieEntity
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import kotlin.test.assertEquals

class MoviesLocalDataSourceTest {

    private val mockMoviesDao: MoviesDAO = mock()
    private val mockMovieDetailsDao: MovieDetailsDAO = mock()

    private val sut: MoviesLocalDataSource =
        MoviesLocalDataSource(
            mockMoviesDao,
            mockMovieDetailsDao,
        )

    @Test
    fun `get movies by ids`() = runBlockingTest {
        val ids = listOf("id")
        val expected = aListOfMovies()
        whenever(mockMoviesDao.getByIds(ids)).thenReturn(expected)

        val actual = sut.getMoviesByIds(ids)

        assertEquals(expected, actual)
    }

    @Test
    fun `delete movie by id`() = runBlockingTest {
        val id = "id"

        sut.deleteMovieById(id)

        verify(mockMoviesDao).deleteById(id)
    }

    @Test
    fun `insert movies`() = runBlockingTest {
        val movies = aListOfMovies()

        sut.insert(movies)

        verify(mockMoviesDao).insert(*movies.toTypedArray())
    }

    @Test
    fun `get movie details by id`() = runBlockingTest {
        val movieId = "movieId"
        val expected = aMovieDetails()
        whenever(mockMovieDetailsDao.getByMovieId(movieId)).thenReturn(expected)

        val actual = sut.getMovieDetailsById(movieId)

        assertEquals(expected, actual)
    }

    @Test
    fun `delete movie details by id`() = runBlockingTest {
        val movieId = "movieId"

        sut.deleteMovieDetailsById(movieId)

        verify(mockMovieDetailsDao).deleteByMovieId(movieId)
    }

    @Test
    fun `insert movie details`() = runBlockingTest {
        val movieDetails = aMovieDetails()

        sut.insert(movieDetails)

        verify(mockMovieDetailsDao).insert(movieDetails)
    }

}

private fun aListOfMovies(): List<MovieEntity> =
    listOf(
        MovieEntity(
            id = "id",
            image = "image",
            title = "title",
            year = 2021,
        )
    )

private fun aMovieDetails(): MovieDetailsEntity =
    MovieDetailsEntity(
        movieId = "movieId",
        rating = 7.5F,
        runtime = 90,
        outline = "outline",
        summary = "summary",
        genres = listOf("genre"),
    )
