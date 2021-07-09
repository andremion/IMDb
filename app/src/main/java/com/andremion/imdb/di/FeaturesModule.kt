package com.andremion.imdb.di

import com.andremion.imdb.ui.details.MovieDetailsFragment
import com.andremion.imdb.ui.details.di.MovieDetailsModule
import com.andremion.imdb.ui.movies.MoviesFragment
import com.andremion.imdb.ui.movies.di.MoviesModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FeaturesModule {

    @ContributesAndroidInjector(modules = [MoviesModule::class])
    fun contributesForMoviesFragment(): MoviesFragment

    @ContributesAndroidInjector(modules = [MovieDetailsModule::class])
    fun contributesForMovieDetailsFragment(): MovieDetailsFragment
}
