package com.andremion.imdb.data.mapper

import com.andremion.imdb.data.local.entity.MovieDetailsEntity
import com.andremion.imdb.data.local.entity.MovieEntity
import com.andremion.imdb.domain.Movie
import com.andremion.imdb.util.transform
import javax.inject.Inject

class MoviesDomainMapper @Inject constructor() {

    fun map(movieId: String): Movie =
        movieId.transform(String::mapToDomain)

    fun map(movie: MovieEntity): Movie =
        movie.mapToDomain(details = movie.mapToDomain(overview = null))

    fun map(movie: MovieEntity, details: MovieDetailsEntity): Movie =
        movie.mapToDomain(details = movie.mapToDomain(overview = details.mapToDomain()))
}

private fun String.mapToDomain(): Movie =
    Movie(
        id = this,
        details = null
    )

private fun MovieEntity.mapToDomain(details: Movie.Details?): Movie =
    Movie(
        id = id,
        details = details
    )

private fun MovieEntity.mapToDomain(overview: Movie.Overview?): Movie.Details =
    Movie.Details(
        image = image,
        title = title,
        year = year,
        overview = overview
    )

private fun MovieDetailsEntity.mapToDomain(): Movie.Overview =
    Movie.Overview(
        rating = rating,
        runtime = runtime,
        outline = outline,
        summary = summary,
        genres = genres,
    )
