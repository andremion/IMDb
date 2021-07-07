package com.andremion.imdb.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class MovieOverviewDTO(
    val ratings: Ratings,
    val title: Title,
    val plotSummary: Plot?,
    val plotOutline: Plot,
    val genres: List<String>,
) {

    @Serializable
    data class Ratings(
        val rating: Float?,
    )

    @Serializable
    data class Title(
        val runningTimeInMinutes: Int,
    )

    @Serializable
    data class Plot(
        val text: String,
    )
}
