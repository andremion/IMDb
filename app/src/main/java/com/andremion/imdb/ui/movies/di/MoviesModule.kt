package com.andremion.imdb.ui.movies.di

import androidx.lifecycle.ViewModel
import com.andremion.imdb.databinding.FragmentMoviesBinding
import com.andremion.imdb.ui.movies.MoviesFragment
import com.andremion.imdb.ui.movies.MoviesViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [MoviesModule.Bindings::class])
object MoviesModule {

    @Provides
    fun providesImageLoader(fragment: MoviesFragment): RequestManager = Glide.with(fragment)

    @Provides
    fun providesBinding(fragment: MoviesFragment): FragmentMoviesBinding =
        FragmentMoviesBinding.bind(fragment.requireView())

    @Module
    interface Bindings {

        @Binds
        fun bindsViewModel(viewModel: MoviesViewModel): ViewModel
    }
}
