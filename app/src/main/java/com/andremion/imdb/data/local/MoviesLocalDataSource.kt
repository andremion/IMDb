package com.andremion.imdb.data.local

import com.andremion.imdb.data.local.entity.MovieDetailsEntity
import com.andremion.imdb.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Source of truth for local data.
 * The local data are modelled as Entities and basically they are two:
 * [MovieEntity] - It has the basic info about a movie
 * [MovieDetailsEntity] - It has more detailed info about a movie
 */
class MoviesLocalDataSource @Inject constructor(
    private val moviesDAO: MoviesDAO,
    private val movieDetailsDAO: MovieDetailsDAO,
) {

    fun getMoviesByIds(ids: List<String>): Flow<List<MovieEntity>> =
        moviesDAO.getByIds(ids)

    suspend fun getMovieById(id: String): MovieEntity? =
        moviesDAO.getById(id)

    suspend fun deleteMovieById(id: String) {
        moviesDAO.deleteById(id)
    }

    suspend fun insert(movie: MovieEntity) {
        moviesDAO.insert(movie)
    }

    suspend fun getMovieDetailsById(id: String): MovieDetailsEntity? =
        movieDetailsDAO.getByMovieId(id)

    suspend fun deleteMovieDetailsById(id: String) {
        movieDetailsDAO.deleteByMovieId(id)
    }

    suspend fun insert(movieDetails: MovieDetailsEntity) {
        movieDetailsDAO.insert(movieDetails)
    }
}
