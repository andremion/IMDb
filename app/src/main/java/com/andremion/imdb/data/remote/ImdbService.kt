package com.andremion.imdb.data.remote

import com.andremion.imdb.data.remote.dto.MovieDetailsDTO
import com.andremion.imdb.data.remote.dto.MovieOverviewDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface ImdbService {

    @GET("title/get-most-popular-movies")
    suspend fun getMostPopularMovies(): List<String>

    @GET("title/get-details")
    suspend fun getDetails(@Query("tconst") titleId: String): MovieDetailsDTO

    @GET("title/get-overview-details")
    suspend fun getOverviewDetails(@Query("tconst") titleId: String): MovieOverviewDTO

}
