package com.andremion.imdb.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.andremion.imdb.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDAO {

    @Query("SELECT * FROM movies WHERE id in (:ids)")
    fun getByIds(ids: List<String>): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getById(id: String): MovieEntity?

    @Insert(entity = MovieEntity::class)
    suspend fun insert(vararg movie: MovieEntity)

    @Query("DELETE FROM movies WHERE id = :id")
    suspend fun deleteById(id: String)
}
