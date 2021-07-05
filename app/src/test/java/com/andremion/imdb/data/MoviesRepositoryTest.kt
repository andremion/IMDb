package com.andremion.imdb.data

import com.andremion.imdb.data.local.MoviesLocalDataSource
import com.andremion.imdb.data.local.entity.MovieEntity
import com.andremion.imdb.data.mapper.MoviesDomainMapper
import com.andremion.imdb.data.mapper.MoviesLocalMapper
import com.andremion.imdb.data.remote.MoviesRemoteDataSource
import com.andremion.imdb.data.remote.dto.MovieDetailsDTO
import com.andremion.imdb.domain.Movie
import com.nhaarman.mockitokotlin2.mock
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
        val ids = listOf("1", "2", "3")
        whenever(mockRemoteDataSource.getMostPopularMovieIds()).thenReturn(ids)
        whenever(mockLocalDataSource.getMoviesByIds(ids)).thenReturn(emptyList())
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
        val ids = listOf("1", "2", "3")
        whenever(mockRemoteDataSource.getMostPopularMovieIds()).thenReturn(ids)
        val entity1 = aMovieEntity("1")
        val entity2 = aMovieEntity("2")
        whenever(mockLocalDataSource.getMoviesByIds(ids)).thenReturn(listOf(entity1, entity2))
        val entity3 = givenAMovieEntity("3")
        val entities = listOf(entity1, entity2, entity3)
        val expected = aListOfMovies()
        whenever(mockDomainMapper.map(entities)).thenReturn(expected)

        val actual = sut.getMostPopularMovies()

        assertEquals(expected, actual)
        verify(mockLocalDataSource).insert(entities)
    }

    @Test
    fun `get most popular movies when local data source has all the movies`() = runBlockingTest {
        val ids = listOf("1", "2", "3")
        whenever(mockRemoteDataSource.getMostPopularMovieIds()).thenReturn(ids)
        val entity1 = aMovieEntity("1")
        val entity2 = aMovieEntity("2")
        val entity3 = aMovieEntity("3")
        val entities = listOf(entity1, entity2, entity3)
        whenever(mockLocalDataSource.getMoviesByIds(ids)).thenReturn(entities)
        val expected = aListOfMovies()
        whenever(mockDomainMapper.map(entities)).thenReturn(expected)

        val actual = sut.getMostPopularMovies()

        assertEquals(expected, actual)
        verify(mockLocalDataSource).insert(entities)
    }

    private suspend fun givenAMovieEntity(id: String): MovieEntity {
        val movieDetails = aMovieDetailsDTO(id)
        whenever(mockRemoteDataSource.getDetails(id)).thenReturn(movieDetails)
        val movieEntity = aMovieEntity(id)
        whenever(mockLocalMapper.map(movieDetails)).thenReturn(movieEntity)
        return movieEntity
    }
}

private fun aMovieEntity(id: String): MovieEntity =
    MovieEntity(
        id = id,
        image = "image",
        title = "title",
        year = 2021,
    )

private fun aMovieDetailsDTO(id: String): MovieDetailsDTO =
    MovieDetailsDTO(
        id = id,
        image = "image",
        title = "title",
        year = 2021,
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
