package com.andremion.imdb.ui.details.model

data class MovieDetailsModel(
    val id: String,
    val image: String,
    val title: String,
    val year: String,
    val rating: String?,
    val runtime: String,
    val outline: String,
    val summary: String?,
    val genres: List<String>,
)
