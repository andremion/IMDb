package com.andremion.imdb.ui.movies

import androidx.core.view.isVisible
import com.andremion.imdb.R
import com.andremion.imdb.databinding.FragmentMoviesBinding
import com.andremion.imdb.util.mvvm.BaseScreen
import com.andremion.imdb.util.setupToolbarWithNavController
import com.bumptech.glide.RequestManager
import com.google.android.material.snackbar.Snackbar
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
                showError(RuntimeException("No movies"))
            }
            is MoviesViewState.Error -> {
                progressView.isVisible = false
                showError(state.error)
            }
        }
    }

    private fun setupRecyclerView() {
        recyclerView.adapter = moviesAdapter
    }

    private fun showError(error: Throwable) {
        val text = if (error.message.isNullOrBlank()) {
            rootView.context.getString(R.string.generic_error)
        } else {
            error.message!!
        }
        Snackbar.make(rootView, text, Snackbar.LENGTH_LONG).show()
    }
}

sealed class MoviesViewEvent {
    data class MovieClicked(val movieId: String) : MoviesViewEvent()
}
