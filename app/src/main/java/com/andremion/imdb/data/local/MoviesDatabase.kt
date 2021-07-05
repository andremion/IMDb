package com.andremion.imdb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.andremion.imdb.data.local.entity.MovieDetailsEntity
import com.andremion.imdb.data.local.entity.MovieEntity
import com.andremion.imdb.data.local.util.Converters

@Database(entities = [MovieEntity::class, MovieDetailsEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDAO(): MoviesDAO
    abstract fun movieDetailsDAO(): MovieDetailsDAO
}
