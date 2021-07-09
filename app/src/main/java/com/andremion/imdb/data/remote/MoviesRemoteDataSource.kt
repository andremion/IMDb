package com.andremion.imdb.data.remote

import com.andremion.imdb.data.remote.dto.MovieDetailsDTO
import com.andremion.imdb.data.remote.dto.MovieOverviewDTO
import com.andremion.imdb.data.remote.mapper.MovieIdMapper
import com.andremion.imdb.util.transform
import javax.inject.Inject

/**
 * Source of truth for remote data.
 * The data returned from the API are modelled as DTOs.
 * It might format the movie id before calling the API
 * because the request title ids are format differently from the response ones.
 */
class MoviesRemoteDataSource @Inject constructor(
    private val service: ImdbService,
    private val movieIdMapper: MovieIdMapper
) {

    suspend fun getMostPopularMovieIds(): List<String> =
        service.getMostPopularMovies()

    suspend fun getDetails(movieId: String): MovieDetailsDTO =
        movieIdMapper.map(movieId)
            .transform { service.getDetails(it) }

    suspend fun getOverview(movieId: String): MovieOverviewDTO =
        movieIdMapper.map(movieId)
            .transform { service.getOverviewDetails(it) }
}
