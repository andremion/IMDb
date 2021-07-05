package com.andremion.imdb.data.mapper

import com.andremion.imdb.data.local.entity.MovieEntity
import com.andremion.imdb.data.remote.dto.MovieDetailsDTO
import javax.inject.Inject

class MoviesLocalMapper @Inject constructor() {

    fun map(dto: MovieDetailsDTO): MovieEntity =
        dto.mapToEntity()
}

private fun MovieDetailsDTO.mapToEntity(): MovieEntity =
    MovieEntity(
        id = id,
        image = image,
        title = title,
        year = year,
    )
