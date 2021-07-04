package com.andremion.imdb.data.remote.dto

data class MovieOverviewDTO(
    val ratings: Ratings,
    val title: Title,
    val plotSummary: Plot?,
    val plotOutline: Plot,
    val genres: List<String>,
) {

    data class Ratings(
        val rating: Float?,
    )

    data class Title(
        val runningTimeInMinutes: Int,
    )

    data class Plot(
        val text: String,
    )
}
