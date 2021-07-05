package com.andremion.imdb.data.mapper

import com.andremion.imdb.data.local.entity.MovieEntity
import com.andremion.imdb.domain.Movie
import javax.inject.Inject

class MoviesDomainMapper @Inject constructor() {

    fun map(entities: List<MovieEntity>): List<Movie> =
        entities.mapToDomain()
}

private fun List<MovieEntity>.mapToDomain(): List<Movie> =
    map(MovieEntity::mapToDomain)

private fun MovieEntity.mapToDomain(): Movie =
    Movie(
        id = id,
        image = image,
        title = title,
        year = year,
        details = null
    )
