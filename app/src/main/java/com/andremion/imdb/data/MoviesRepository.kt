package com.andremion.imdb.data

import com.andremion.imdb.data.local.MoviesLocalDataSource
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
        val entities = syncCache(movieIds, cacheMap)
        return entities.transform(domainMapper::map)
    }

//    suspend fun getMovieDetails(movie: Movie): Movie {
//        val cachedDetails = localDataSource.getMovieDetailsById(movie.id)
//        val details = if (cachedDetails == null) {
//            val detailsEntity = remoteDataSource.getOverviewDetails(movie.id)
//                .let { localMapper.map(movie.id, it) }
//            localDataSource.insert(detailsEntity)
//            domainMapper.map(detailsEntity)
//        } else {
//            domainMapper.map(cachedDetails)
//        }
//        return movie.copy(details = details)
//    }

    private suspend fun getCachedMovieMap(movieIds: List<String>): Map<String, MovieEntity> =
        localDataSource.getMoviesByIds(movieIds).associateBy(MovieEntity::id)

    private suspend fun getRefreshedMovie(movieId: String): MovieEntity =
        remoteDataSource.getDetails(movieId).transform(localMapper::map)

    private suspend fun syncCache(movieIds: List<String>, cacheMap: Map<String, MovieEntity>): List<MovieEntity> {
        val newEntities = mutableListOf<MovieEntity>()
        val result = movieIds.map { movieId ->
            cacheMap[movieId] ?: getRefreshedMovie(movieId).also { newEntities += it }
        }
        localDataSource.insert(newEntities)
        return result
    }
}
