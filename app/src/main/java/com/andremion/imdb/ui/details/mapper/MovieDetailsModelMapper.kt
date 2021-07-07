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
        MovieDetailsModel(
            id = id,
            image = image,
            title = title,
            year = year.toString(),
            rating = parseRating(details.rating),
            runtime = parseRuntime(details.runtime),
            outline = details.outline,
            summary = details.summary,
            genres = details.genres,
        )
    }

private fun parseRating(rating: Float?): String =
    rating?.let { "%.0f".format(it) }
        .orEmpty()

private fun parseRuntime(runtime: Int): String {
    val hours = runtime / 60
    val minutes = runtime % 60
    return "%dh %02dm".format(hours, minutes)
}
