package com.andremion.imdb.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsDTO(
    val id: String,
    val image: Image,
    val title: String,
    val year: Int,
) {

    @Serializable
    data class Image(
        val url: String,
    )
}
