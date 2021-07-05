package com.andremion.imdb.domain

data class Movie(
    val id: String,
    val image: String,
    val title: String,
    val year: Int,
    val details: Details?
) {

    data class Details(
        val rating: Float?,
        val runtime: Int,
        val outline: String,
        val summary: String?,
        val genres: List<String>,
    )
}
