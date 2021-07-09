package com.andremion.imdb.ui.movies.model

data class MovieModel(
    val isEnabled: Boolean,
    val isLoading: Boolean,
    val id: String,
    val image: String,
    val title: String,
    val year: String
)
