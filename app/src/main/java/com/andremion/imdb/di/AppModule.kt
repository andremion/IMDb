package com.andremion.imdb.di

import android.app.Application
import android.content.Context
import androidx.work.WorkManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [AppModule.Bindings::class])
object AppModule {

    @Provides
    @Singleton
    fun providesWorkManager(application: Application): WorkManager =
        WorkManager.getInstance(application)

    @Module
    interface Bindings {

        @Binds
        @Singleton
        fun bindsContext(application: Application): Context
    }
}
