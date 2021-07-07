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
    binding: FragmentMovieDetailsBinding,
    private val imageLoader: RequestManager
) : BaseScreen<MovieDetailsViewEvent>() {

    private val rootView = binding.root
    private val imageLargeView = binding.imageLarge
    private val imagePosterView = binding.content.imagePoster
    private val titleView = binding.content.title
    private val yearView = binding.content.year
    private val ratingView = binding.content.rating
    private val runtimeView = binding.content.runtime
    private val summaryView = binding.content.summary

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
                rootView.showError(state.error)
            }
        }
    }

    private fun onResult(state: MovieDetailsViewState.Result) {
        titleView.text = state.movieDetails.title
        yearView.text = state.movieDetails.year
        ratingView.text = state.movieDetails.rating
        runtimeView.isVisible = state.movieDetails.rating?.isNotEmpty() ?: false
        runtimeView.text = state.movieDetails.runtime
        summaryView.text = state.movieDetails.summary
        imageLoader.loadImage(state.movieDetails.image, imageLargeView)
        imageLoader.loadImage(state.movieDetails.image, imagePosterView)
    }
}

sealed class MovieDetailsViewEvent
