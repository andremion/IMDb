package com.andremion.imdb.ui.movies

import androidx.core.view.isVisible
import com.andremion.imdb.databinding.FragmentMoviesBinding
import com.andremion.imdb.util.mvvm.BaseScreen
import com.andremion.imdb.util.setupToolbarWithNavController
import com.andremion.imdb.util.showError
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class MoviesScreen @Inject constructor(
    binding: FragmentMoviesBinding,
    imageLoader: RequestManager
) : BaseScreen<MoviesViewEvent>() {

    private val rootView = binding.root
    private val recyclerView = binding.movieList
    private val progressView = binding.progress

    private val moviesAdapter = MoviesAdapter(imageLoader) { movie ->
        _event.tryEmit(MoviesViewEvent.MovieClicked(movie.id))
    }

    init {
        binding.toolbar.setupToolbarWithNavController()
        setupRecyclerView()
    }

    fun render(state: MoviesViewState) {
        when (state) {
            is MoviesViewState.Idle -> {
                progressView.isVisible = false
            }
            is MoviesViewState.Loading -> {
                progressView.isVisible = true
                moviesAdapter.submitList(emptyList())
            }
            is MoviesViewState.Result -> {
                progressView.isVisible = false
                moviesAdapter.submitList(state.movies)
            }
            is MoviesViewState.EmptyResult -> {
                progressView.isVisible = false
                moviesAdapter.submitList(emptyList())
                // TODO Empty UI
                rootView.showError(RuntimeException("No movies"))
            }
            is MoviesViewState.Error -> {
                progressView.isVisible = false
                rootView.showError(state.error)
            }
        }
    }

    private fun setupRecyclerView() {
        recyclerView.adapter = moviesAdapter
    }
}

sealed class MoviesViewEvent {
    data class MovieClicked(val movieId: String) : MoviesViewEvent()
}
