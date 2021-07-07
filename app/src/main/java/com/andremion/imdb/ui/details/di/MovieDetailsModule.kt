package com.andremion.imdb.ui.details.di

import androidx.lifecycle.ViewModel
import com.andremion.imdb.databinding.FragmentMovieDetailsBinding
import com.andremion.imdb.ui.details.MovieDetailsFragment
import com.andremion.imdb.ui.details.MovieDetailsViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [MovieDetailsModule.Bindings::class])
object MovieDetailsModule {

    @Provides
    fun providesImageLoader(fragment: MovieDetailsFragment): RequestManager = Glide.with(fragment)

    @Provides
    fun providesBinding(fragment: MovieDetailsFragment): FragmentMovieDetailsBinding =
        FragmentMovieDetailsBinding.bind(fragment.requireView())

    @Module
    interface Bindings {

        @Binds
        fun bindsViewModel(viewModel: MovieDetailsViewModel): ViewModel
    }
}
