package com.andremion.imdb.data.mapper

import com.andremion.imdb.data.local.entity.MovieDetailsEntity
import com.andremion.imdb.data.local.entity.MovieEntity
import com.andremion.imdb.data.remote.dto.MovieDetailsDTO
import com.andremion.imdb.data.remote.dto.MovieOverviewDTO
import javax.inject.Inject

class MoviesLocalMapper @Inject constructor() {

    fun map(dto: MovieDetailsDTO): MovieEntity =
        dto.mapToEntity()

    fun map(movieId: String, dto: MovieOverviewDTO): MovieDetailsEntity =
        dto.mapToEntity(movieId)
}

private fun MovieDetailsDTO.mapToEntity(): MovieEntity =
    MovieEntity(
        id = id,
        image = image.url,
        title = title,
        year = year,
    )

private fun MovieOverviewDTO.mapToEntity(movieId: String): MovieDetailsEntity =
    MovieDetailsEntity(
        movieId = movieId,
        rating = ratings.rating,
        runtime = title.runningTimeInMinutes,
        outline = plotOutline.text,
        summary = plotSummary?.text,
        genres = genres
    )
