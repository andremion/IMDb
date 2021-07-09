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
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
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
        val movie1 = aMovie("1")
        val movie2 = aMovie("2")
        val movie3 = aMovie("3")
        whenever(mockRemoteDataSource.getMostPopularMovieIds()).thenReturn(movieIds)
        whenever(mockLocalDataSource.getMoviesByIds(movieIds)).thenReturn(flowOf(emptyList()))
        whenever(mockDomainMapper.map("1")).thenReturn(movie1)
        whenever(mockDomainMapper.map("2")).thenReturn(movie2)
        whenever(mockDomainMapper.map("3")).thenReturn(movie3)

        val actual = sut.getMostPopularMovies().single()

        val expected = listOf(movie1, movie2, movie3)
        assertEquals(expected, actual)
    }

    @Test
    fun `get most popular movies when local data source has some movies`() = runBlockingTest {
        val movieIds = listOf("1", "2", "3")
        val entity1 = aMovieEntity("1")
        val entity2 = aMovieEntity("2")
        val movie1 = aMovie("1")
        val movie2 = aMovie("2")
        val movie3 = aMovie("3")
        whenever(mockRemoteDataSource.getMostPopularMovieIds()).thenReturn(movieIds)
        whenever(mockLocalDataSource.getMoviesByIds(movieIds)).thenReturn(flowOf(listOf(entity1, entity2)))
        whenever(mockDomainMapper.map(entity1)).thenReturn(movie1)
        whenever(mockDomainMapper.map(entity2)).thenReturn(movie2)
        whenever(mockDomainMapper.map("3")).thenReturn(movie3)

        val actual = sut.getMostPopularMovies().single()

        val expected = listOf(movie1, movie2, movie3)
        assertEquals(expected, actual)
    }

    @Test
    fun `get most popular movies when local data source has all the movies`() = runBlockingTest {
        val movieIds = listOf("1", "2", "3")
        val entity1 = aMovieEntity("1")
        val entity2 = aMovieEntity("2")
        val entity3 = aMovieEntity("3")
        val movie1 = aMovie("1")
        val movie2 = aMovie("2")
        val movie3 = aMovie("3")
        whenever(mockRemoteDataSource.getMostPopularMovieIds()).thenReturn(movieIds)
        whenever(mockLocalDataSource.getMoviesByIds(movieIds)).thenReturn(flowOf(listOf(entity1, entity2, entity3)))
        whenever(mockDomainMapper.map(entity1)).thenReturn(movie1)
        whenever(mockDomainMapper.map(entity2)).thenReturn(movie2)
        whenever(mockDomainMapper.map(entity3)).thenReturn(movie3)

        val actual = sut.getMostPopularMovies().single()

        val expected = listOf(movie1, movie2, movie3)
        assertEquals(expected, actual)
    }

    @Test
    fun `fetch movie details`() = runBlockingTest {
        val movieId = "1"
        val movieDetailsDTO = aMovieDetailsDTO(movieId)
        whenever(mockRemoteDataSource.getDetails(movieId)).thenReturn(movieDetailsDTO)
        val movieEntity = aMovieEntity(movieId)
        whenever(mockLocalMapper.map(movieDetailsDTO)).thenReturn(movieEntity)

        sut.fetchMovieDetails(movieId)

        verify(mockLocalDataSource).insert(movieEntity)
    }

    @Test
    fun `get movie overview when local data source is empty`() = runBlockingTest {
        val movieId = "1"
        val movie = givenAMovieEntity(movieId)
        val details = givenAMovieDetailsEntity(movie.id)
        val expected = aMovieOverview(movieId)
        whenever(mockDomainMapper.map(movie, details)).thenReturn(expected)

        val actual = sut.getMovieOverview(movieId)

        assertEquals(expected, actual)
        verify(mockLocalDataSource).insert(details)
    }

    @Test
    fun `get movie overview when local data source has only movie, not the details`() = runBlockingTest {
        val movieId = "1"
        val movie = aMovieEntity(movieId)
        whenever(mockLocalDataSource.getMovieById(movie.id)).thenReturn(movie)
        val details = givenAMovieDetailsEntity(movie.id)
        val expected = aMovieOverview(movieId)
        whenever(mockDomainMapper.map(movie, details)).thenReturn(expected)

        val actual = sut.getMovieOverview(movieId)

        assertEquals(expected, actual)
        verify(mockLocalDataSource).insert(details)
    }

    @Test
    fun `get movie overview when local data source has both movie and details`() = runBlockingTest {
        val movieId = "1"
        val movie = aMovieEntity(movieId)
        whenever(mockLocalDataSource.getMovieById(movie.id)).thenReturn(movie)
        val details = aMovieDetailsEntity(movie.id)
        whenever(mockLocalDataSource.getMovieDetailsById(movie.id)).thenReturn(details)
        val expected = aMovieOverview(movieId)
        whenever(mockDomainMapper.map(movie, details)).thenReturn(expected)

        val actual = sut.getMovieOverview(movieId)

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
        whenever(mockRemoteDataSource.getOverview(movieId)).thenReturn(movieOverview)
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
        image = MovieDetailsDTO.Image(url = "image"),
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
