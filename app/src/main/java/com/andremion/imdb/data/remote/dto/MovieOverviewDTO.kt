package com.andremion.imdb.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class MovieOverviewDTO(
    val ratings: Ratings,
    val title: Title,
    val plotSummary: Plot? = null,
    val plotOutline: Plot,
    val genres: List<String>,
) {

    @Serializable
    data class Ratings(
        val rating: Float? = null,
    )

    @Serializable
    data class Title(
        val runningTimeInMinutes: Int? = null,
    )

    @Serializable
    data class Plot(
        val text: String,
    )
}
