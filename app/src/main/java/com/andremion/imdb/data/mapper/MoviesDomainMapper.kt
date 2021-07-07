package com.andremion.imdb.data.mapper

import com.andremion.imdb.data.local.entity.MovieDetailsEntity
import com.andremion.imdb.data.local.entity.MovieEntity
import com.andremion.imdb.domain.Movie
import javax.inject.Inject

class MoviesDomainMapper @Inject constructor() {

    fun map(entities: List<MovieEntity>): List<Movie> =
        entities.mapToDomain()

    fun map(domain: Movie, entity: MovieDetailsEntity): Movie =
        domain.copy(details = entity.mapToDomain())
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

private fun MovieDetailsEntity.mapToDomain(): Movie.Details =
    Movie.Details(
        rating = rating,
        runtime = runtime,
        outline = outline,
        summary = summary,
        genres = genres,
    )
