package com.andremion.imdb.data

import com.andremion.imdb.data.local.MoviesLocalDataSource
import com.andremion.imdb.data.local.entity.MovieDetailsEntity
import com.andremion.imdb.data.local.entity.MovieEntity
import com.andremion.imdb.data.mapper.MoviesDomainMapper
import com.andremion.imdb.data.mapper.MoviesLocalMapper
import com.andremion.imdb.data.remote.MoviesRemoteDataSource
import com.andremion.imdb.domain.Movie
import com.andremion.imdb.util.transform
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val localDataSource: MoviesLocalDataSource,
    private val remoteDataSource: MoviesRemoteDataSource,
    private val domainMapper: MoviesDomainMapper,
    private val localMapper: MoviesLocalMapper
) {

    suspend fun getMostPopularMovies(): List<Movie> {
        val movieIds = remoteDataSource.getMostPopularMovieIds()
        val cacheMap = getCachedMovieMap(movieIds)
        val entities = getEntitiesSyncedFromCache(movieIds, cacheMap)
        return entities.transform(domainMapper::map)
    }

    suspend fun getMovieDetails(movieId: String): Movie {
        val cachedMovie = localDataSource.getMoviesById(movieId) ?: getMovieSyncedFromCache(movieId)
        val cachedDetails = localDataSource.getMovieDetailsById(cachedMovie.id)
        val details = cachedDetails ?: getRefreshedMovieDetails(cachedMovie.id)
            .also { localDataSource.insert(it) }
        return domainMapper.map(cachedMovie, details)
    }

    private suspend fun getCachedMovieMap(movieIds: List<String>): Map<String, MovieEntity> =
        localDataSource.getMoviesByIds(movieIds)
            .associateBy(MovieEntity::id)

    private suspend fun getMovieSyncedFromCache(movieId: String): MovieEntity =
        getRefreshedMovie(movieId)
            .also { localDataSource.insert(it) }

    private suspend fun getRefreshedMovie(movieId: String): MovieEntity =
        remoteDataSource.getDetails(movieId)
            .transform(localMapper::map)

    private suspend fun getRefreshedMovieDetails(movieId: String): MovieDetailsEntity =
        remoteDataSource.getOverviewDetails(movieId)
            .transform { localMapper.map(movieId, it) }

    private suspend fun getEntitiesSyncedFromCache(
        movieIds: List<String>,
        cacheMap: Map<String, MovieEntity>
    ): List<MovieEntity> {
        val newEntities = mutableListOf<MovieEntity>()
        val result = movieIds.map { movieId ->
            cacheMap[movieId] ?: getRefreshedMovie(movieId)
                .also { newEntities += it }
        }
        localDataSource.insert(newEntities)
        return result
    }
}
