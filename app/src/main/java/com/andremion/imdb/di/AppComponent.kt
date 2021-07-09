package com.andremion.imdb.di

import android.app.Application
import android.content.Context
import com.andremion.imdb.ImdbApp
import com.andremion.imdb.data.di.DataModule
import com.andremion.imdb.data.local.di.LocalModule
import com.andremion.imdb.data.remote.di.RemoteModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        LocalModule::class,
        RemoteModule::class,
        DataModule::class,
        FeaturesModule::class,
        WorkersModule::class,
    ]
)
@Singleton
interface AppComponent : AndroidInjector<ImdbApp> {

    val context: Context

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance application: Application): AppComponent
    }
}
