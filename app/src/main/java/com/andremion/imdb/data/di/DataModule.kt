package com.andremion.imdb.data.di

import androidx.work.ListenableWorker
import com.andremion.imdb.data.worker.FetchMovieDetailsWorker
import dagger.Binds
import dagger.Module

@Module(includes = [DataModule.Bindings::class])
object DataModule {

    @Module
    interface Bindings {

        @Binds
        fun bindsFetchMovieDetailsWorker(worker: FetchMovieDetailsWorker): ListenableWorker
    }
}
