package com.andremion.imdb.di

import com.andremion.imdb.ui.movies.MoviesFragment
import com.andremion.imdb.ui.movies.di.MoviesModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FeaturesModule {

    @ContributesAndroidInjector(modules = [MoviesModule::class])
    fun bindsMoviesFragment(): MoviesFragment
}
