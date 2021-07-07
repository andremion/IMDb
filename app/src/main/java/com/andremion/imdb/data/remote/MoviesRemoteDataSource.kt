package com.andremion.imdb.data.remote

import com.andremion.imdb.data.remote.dto.MovieDetailsDTO
import com.andremion.imdb.data.remote.dto.MovieOverviewDTO
import com.andremion.imdb.data.remote.mapper.MovieIdMapper
import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(
    private val service: ImdbService,
    private val movieIdMapper: MovieIdMapper
) {

    suspend fun getMostPopularMovieIds(): List<String> =
        service.getMostPopularMovies()
            .map(movieIdMapper::map)

    suspend fun getDetails(movieId: String): MovieDetailsDTO =
        service.getDetails(movieId)

    suspend fun getOverviewDetails(movieId: String): MovieOverviewDTO =
        service.getOverviewDetails(movieId)
}
