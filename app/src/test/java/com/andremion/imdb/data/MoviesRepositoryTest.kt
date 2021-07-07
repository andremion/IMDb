package com.andremion.imdb.data

import com.andremion.imdb.data.local.MoviesLocalDataSource
import com.andremion.imdb.data.local.entity.MovieDetailsEntity
import com.andremion.imdb.data.local.entity.MovieEntity
import com.andremion.imdb.data.mapper.MoviesDomainMapper
import com.andremion.imdb.data.mapper.MoviesLocalMapper
import com.andremion.imdb.data.remote.MoviesRemoteDataSource
import com.andremion.imdb.data.remote.dto.MovieDetailsDTO
import com.andremion.imdb.data.remote.dto.MovieOverviewDTO
import com.andremion.imdb.domain.Movie
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import kotlin.test.assertEquals

class MoviesRepositoryTest {

    private val mockLocalDataSource: MoviesLocalDataSource = mock()
    private val mockRemoteDataSource: MoviesRemoteDataSource = mock()
    private val mockDomainMapper: MoviesDomainMapper = mock()
    private val mockLocalMapper: MoviesLocalMapper = mock()

    private val sut: MoviesRepository =
        MoviesRepository(
            mockLocalDataSource,
            mockRemoteDataSource,
            mockDomainMapper,
            mockLocalMapper
        )

    @Test
    fun `get most popular movies when local data source is empty`() = runBlockingTest {
        val movieIds = listOf("1", "2", "3")
        whenever(mockRemoteDataSource.getMostPopularMovieIds()).thenReturn(movieIds)
        whenever(mockLocalDataSource.getMoviesByIds(movieIds)).thenReturn(emptyList())
        val entity1 = givenAMovieEntity("1")
        val entity2 = givenAMovieEntity("2")
        val entity3 = givenAMovieEntity("3")
        val entities = listOf(entity1, entity2, entity3)
        val expected = aListOfMovies()
        whenever(mockDomainMapper.map(entities)).thenReturn(expected)

        val actual = sut.getMostPopularMovies()

        assertEquals(expected, actual)
        verify(mockLocalDataSource).insert(entities)
    }

    @Test
    fun `get most popular movies when local data source has some movies`() = runBlockingTest {
        val movieIds = listOf("1", "2", "3")
        whenever(mockRemoteDataSource.getMostPopularMovieIds()).thenReturn(movieIds)
        val entity1 = aMovieEntity("1")
        val entity2 = aMovieEntity("2")
        whenever(mockLocalDataSource.getMoviesByIds(movieIds)).thenReturn(listOf(entity1, entity2))
        val entity3 = givenAMovieEntity("3")
        val entities = listOf(entity1, entity2, entity3)
        val expected = aListOfMovies()
        whenever(mockDomainMapper.map(entities)).thenReturn(expected)

        val actual = sut.getMostPopularMovies()

        assertEquals(expected, actual)
        verify(mockLocalDataSource).insert(listOf(entity3))
    }

    @Test
    fun `get most popular movies when local data source has all the movies`() = runBlockingTest {
        val movieIds = listOf("1", "2", "3")
        whenever(mockRemoteDataSource.getMostPopularMovieIds()).thenReturn(movieIds)
        val entity1 = aMovieEntity("1")
        val entity2 = aMovieEntity("2")
        val entity3 = aMovieEntity("3")
        val entities = listOf(entity1, entity2, entity3)
        whenever(mockLocalDataSource.getMoviesByIds(movieIds)).thenReturn(entities)
        val expected = aListOfMovies()
        whenever(mockDomainMapper.map(entities)).thenReturn(expected)

        val actual = sut.getMostPopularMovies()

        assertEquals(expected, actual)
        verify(mockLocalDataSource).insert(emptyList())
    }

    @Test
    fun `get movie details when local data source is empty`() = runBlockingTest {
        val movie = aMovie()
        whenever(mockLocalDataSource.getMovieDetailsById(movie.id)).thenReturn(null)
        val details = givenAMovieDetailsEntity(movie.id)
        val expected = aDetailedMovie()
        whenever(mockDomainMapper.map(movie, details)).thenReturn(expected)

        val actual = sut.getMovieDetails(movie)

        assertEquals(expected, actual)
        verify(mockLocalDataSource).insert(details)
    }

    @Test
    fun `get movie details when local data source has the movie details`() = runBlockingTest {
        val movie = aMovie()
        val details = givenAMovieDetailsEntity(movie.id)
        whenever(mockLocalDataSource.getMovieDetailsById(movie.id)).thenReturn(details)
        val expected = aDetailedMovie()
        whenever(mockDomainMapper.map(movie, details)).thenReturn(expected)

        val actual = sut.getMovieDetails(movie)

        assertEquals(expected, actual)
        verify(mockLocalDataSource, never()).insert(details)
    }

    private suspend fun givenAMovieEntity(movieId: String): MovieEntity {
        val movieDetails = aMovieDetailsDTO(movieId)
        whenever(mockRemoteDataSource.getDetails(movieId)).thenReturn(movieDetails)
        val movieEntity = aMovieEntity(movieId)
        whenever(mockLocalMapper.map(movieDetails)).thenReturn(movieEntity)
        return movieEntity
    }

    private suspend fun givenAMovieDetailsEntity(movieId: String): MovieDetailsEntity {
        val movieOverview = aMovieOverviewDTO()
        whenever(mockRemoteDataSource.getOverviewDetails(movieId)).thenReturn(movieOverview)
        val movieDetailsEntity = aMovieDetailsEntity(movieId)
        whenever(mockLocalMapper.map(movieId, movieOverview)).thenReturn(movieDetailsEntity)
        return movieDetailsEntity
    }
}

private fun aMovieEntity(movieId: String): MovieEntity =
    MovieEntity(
        id = movieId,
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

private fun aMovieDetailsDTO(movieId: String): MovieDetailsDTO =
    MovieDetailsDTO(
        id = movieId,
        image = "image",
        title = "title",
        year = 2021,
    )

private fun aMovieOverviewDTO(): MovieOverviewDTO =
    MovieOverviewDTO(
        ratings = MovieOverviewDTO.Ratings(rating = 7.5f),
        title = MovieOverviewDTO.Title(runningTimeInMinutes = 90),
        plotSummary = MovieOverviewDTO.Plot(text = "text"),
        plotOutline = MovieOverviewDTO.Plot(text = "text"),
        genres = listOf("genre"),
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
