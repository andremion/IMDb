package com.andremion.imdb.di

import com.andremion.imdb.data.worker.FetchMovieDetailsWorker
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface WorkersModule {

    @ContributesAndroidInjector
    fun contributesForFetchMovieDetailsWorker(): FetchMovieDetailsWorker
}
