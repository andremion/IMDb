package com.andremion.imdb.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "movie_details",
    foreignKeys = [ForeignKey(
        entity = MovieEntity::class,
        parentColumns = ["id"],
        childColumns = ["movieId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class MovieDetailsEntity(
    @PrimaryKey val movieId: String,
    val rating: Float?,
    val runtime: Int,
    val outline: String,
    val summary: String?,
    val genres: List<String>,
)
