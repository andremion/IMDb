package com.andremion.imdb.domain

import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: String,
    val details: Details?,
) {

    @Serializable
    data class Details(
        val image: String,
        val title: String,
        val year: Int,
        val overview: Overview?
    )

    @Serializable
    data class Overview(
        val rating: Float?,
        val runtime: Int?,
        val outline: String,
        val summary: String?,
        val genres: List<String>,
    )
}
