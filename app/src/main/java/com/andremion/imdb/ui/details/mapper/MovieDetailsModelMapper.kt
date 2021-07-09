package com.andremion.imdb.ui.details.mapper

import com.andremion.imdb.domain.Movie
import com.andremion.imdb.ui.details.model.MovieDetailsModel
import com.andremion.imdb.util.transform
import javax.inject.Inject

class MovieDetailsModelMapper @Inject constructor() {

    fun map(movie: Movie): MovieDetailsModel =
        movie.transform(Movie::mapToModel)
}

private fun Movie.mapToModel(): MovieDetailsModel =
    requireNotNull(details).transform { details ->
        requireNotNull(details.overview).transform { overview ->
            MovieDetailsModel(
                id = id,
                image = details.image,
                title = details.title,
                year = details.year.toString(),
                rating = parseRating(overview.rating),
                runtime = parseRuntime(overview.runtime),
                summary = overview.summary ?: overview.outline,
                genres = overview.genres,
            )
        }
    }

private fun parseRating(rating: Float?): String =
    rating?.let { "%.1f".format(it) }
        .orEmpty()

private fun parseRuntime(runtime: Int?): String =
    runtime?.let { it ->
        val hours = it / 60
        val minutes = it % 60
        return "%dh %02dm".format(hours, minutes)
    }.orEmpty()
