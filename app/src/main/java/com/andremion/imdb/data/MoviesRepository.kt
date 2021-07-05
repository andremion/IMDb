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
        val ids = remoteDataSource.getMostPopularMovieIds()
        val entitiesMap = localDataSource.getMoviesByIds(ids).associateBy(MovieEntity::id)
        val entities = ids.map { id ->
            entitiesMap[id] ?: remoteDataSource.getDetails(id).transform(localMapper::map)
        }
        localDataSource.insert(entities)
        return entities.transform(domainMapper::map)
    }
}
