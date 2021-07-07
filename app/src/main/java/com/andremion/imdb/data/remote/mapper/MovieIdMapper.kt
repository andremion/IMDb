package com.andremion.imdb.data.remote.mapper

import com.andremion.imdb.util.transform
import javax.inject.Inject

class MovieIdMapper @Inject constructor() {

    private val regex: Regex = "tt+(\\d)*".toRegex()

    fun map(movieId: String): String =
        requireNotNull(regex.find(movieId)) { "Malformed movie id" }
            .transform(MatchResult::value)
}
