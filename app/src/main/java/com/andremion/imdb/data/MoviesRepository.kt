package com.andremion.imdb.data

import com.andremion.imdb.data.local.MoviesLocalDataSource
import com.andremion.imdb.data.local.entity.MovieDetailsEntity
import com.andremion.imdb.data.local.entity.MovieEntity
import com.andremion.imdb.data.mapper.MoviesDomainMapper
import com.andremion.imdb.data.mapper.MoviesLocalMapper
import com.andremion.imdb.data.remote.MoviesRemoteDataSource
import com.andremion.imdb.domain.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val localDataSource: MoviesLocalDataSource,
    private val remoteDataSource: MoviesRemoteDataSource,
    private val domainMapper: MoviesDomainMapper,
    private val localMapper: MoviesLocalMapper,
) {

    fun getMostPopularMovies(): Flow<List<Movie>> =
        remoteDataSource::getMostPopularMovieIds.asFlow()
            .combineWithCache()
            .map { it.toDomain() }

    suspend fun fetchMovieDetails(movieId: String) {
        getRefreshedMovie(movieId).also { localDataSource.insert(it) }
    }

    suspend fun getMovieOverview(movieId: String): Movie {
        val movie = localDataSource.getMovieById(movieId)
            ?: getRefreshedMovie(movieId).also { localDataSource.insert(it) }
        val details = localDataSource.getMovieDetailsById(movie.id)
            ?: getRefreshedMovieDetails(movieId).also { localDataSource.insert(it) }
        return domainMapper.map(movie, details)
    }

    private fun Flow<List<String>>.combineWithCache(): Flow<Map<String, MovieEntity?>> =
        flatMapConcat { ids ->
            localDataSource.getMoviesByIds(ids).map { movies ->
                val idsMap: Map<String, MovieEntity?> = ids.associateWith { null }
                val moviesMap = movies.associateBy(MovieEntity::id)
                idsMap.mapValues { (id) -> moviesMap[id] }
            }
        }

    private fun Map<String, MovieEntity?>.toDomain() =
        map { (id, movie) ->
            if (movie == null) {
                domainMapper.map(id)
            } else {
                domainMapper.map(movie)
            }
        }

    private suspend fun getRefreshedMovie(movieId: String): MovieEntity =
        remoteDataSource.getDetails(movieId)
            .let(localMapper::map)

    private suspend fun getRefreshedMovieDetails(movieId: String): MovieDetailsEntity =
        remoteDataSource.getOverview(movieId)
            .let { localMapper.map(movieId, it) }
}
