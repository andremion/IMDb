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
    if (details == null) {
        MovieModel(
            isEnabled = false,
            isLoading = true,
            id = id,
            image = "",
            title = "",
            year = ""
        )
    } else {
        MovieModel(
            isEnabled = true,
            isLoading = false,
            id = id,
            image = details.image,
            title = details.title,
            year = details.year.toString()
        )
    }

