package com.andremion.imdb.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andremion.imdb.data.local.entity.MovieEntity

@Dao
interface MoviesDAO {

    @Query("SELECT * FROM movies WHERE id in (:ids)")
    suspend fun getByIds(ids: List<String>): List<MovieEntity>

    @Insert(entity = MovieEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg movie: MovieEntity)

    @Query("DELETE FROM movies WHERE id = :id")
    suspend fun deleteById(id: String)
}
