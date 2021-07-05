package com.andremion.imdb.data.local.di

import android.content.Context
import androidx.room.Room
import com.andremion.imdb.data.local.MovieDetailsDAO
import com.andremion.imdb.data.local.MoviesDAO
import com.andremion.imdb.data.local.MoviesDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

private const val DATABASE_NAME = "imdb"

@Module
object LocalModule {

    @Provides
    @Singleton
    fun providesDatabase(context: Context): MoviesDatabase =
        Room.databaseBuilder(context, MoviesDatabase::class.java, DATABASE_NAME).build()

    @Provides
    @Singleton
    fun providesMoviesDAO(db: MoviesDatabase): MoviesDAO =
        db.moviesDAO()

    @Provides
    @Singleton
    fun providesMovieDetailsDAO(db: MoviesDatabase): MovieDetailsDAO =
        db.movieDetailsDAO()
}
