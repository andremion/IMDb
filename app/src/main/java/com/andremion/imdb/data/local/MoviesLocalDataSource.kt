package com.andremion.imdb.data.local

import com.andremion.imdb.data.local.entity.MovieDetailsEntity
import com.andremion.imdb.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

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
