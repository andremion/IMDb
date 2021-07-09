package com.andremion.imdb.ui.details

import androidx.core.view.isVisible
import com.andremion.imdb.databinding.FragmentMovieDetailsBinding
import com.andremion.imdb.util.loadImage
import com.andremion.imdb.util.mvvm.BaseScreen
import com.andremion.imdb.util.setupToolbarWithNavController
import com.andremion.imdb.util.showError
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class MovieDetailsScreen @Inject constructor(
    private val binding: FragmentMovieDetailsBinding,
    private val imageLoader: RequestManager
) : BaseScreen<MovieDetailsViewEvent>() {

    init {
        binding.toolbar?.setupToolbarWithNavController()
    }

    fun render(state: MovieDetailsViewState) {
        when (state) {
            is MovieDetailsViewState.Idle -> {
            }
            is MovieDetailsViewState.Loading -> {
            }
            is MovieDetailsViewState.Result -> {
                onResult(state)
            }
            is MovieDetailsViewState.Error -> {
                binding.root.showError(state.error)
            }
        }
    }

    private fun onResult(state: MovieDetailsViewState.Result) {
        with(binding) {
            imageLoader.loadImage(state.movieDetails.image, imageLarge)
            content.title.text = state.movieDetails.title
            content.year.text = state.movieDetails.year
            content.rating.isVisible = state.movieDetails.rating.isNotEmpty()
            content.rating.text = state.movieDetails.rating
            content.runtime.text = state.movieDetails.runtime
            content.summary.text = state.movieDetails.summary
            imageLoader.loadImage(state.movieDetails.image, content.imagePoster)
        }
        _event.tryEmit(MovieDetailsViewEvent.ResultBound)
    }
}

sealed class MovieDetailsViewEvent {
    object ResultBound : MovieDetailsViewEvent()
}
