package com.andremion.imdb.ui.movies.mapper

import com.andremion.imdb.domain.Movie
import com.andremion.imdb.ui.movies.model.MovieModel
import javax.inject.Inject

class MoviesModelMapper @Inject constructor() {

    fun map(movies: List<Movie>): List<MovieModel> =
        movies.mapToModel()
}

private fun List<Movie>.mapToModel(): List<MovieModel> =
    map(Movie::mapToModel)

private fun Movie.mapToModel(): MovieModel =
    MovieModel(
        id = id,
        image = image,
        title = title,
        year = year.toString()
    )
