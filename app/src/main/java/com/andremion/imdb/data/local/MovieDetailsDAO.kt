package com.andremion.imdb.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.andremion.imdb.data.local.entity.MovieDetailsEntity

@Dao
interface MovieDetailsDAO {

    @Query("SELECT * FROM movie_details WHERE movieId = :movieId")
    suspend fun getByMovieId(movieId: String): MovieDetailsEntity

    @Insert(entity = MovieDetailsEntity::class)
    suspend fun insert(movieDetails: MovieDetailsEntity)

    @Query("DELETE FROM movie_details WHERE movieId = :movieId")
    suspend fun deleteByMovieId(movieId: String)
}
