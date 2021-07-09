package com.andremion.imdb.ui.movies

import androidx.core.view.isVisible
import com.andremion.imdb.databinding.FragmentMoviesBinding
import com.andremion.imdb.ui.movies.model.MovieModel
import com.andremion.imdb.util.mvvm.BaseScreen
import com.andremion.imdb.util.setupToolbarWithNavController
import com.andremion.imdb.util.showError
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class MoviesScreen @Inject constructor(
    private val binding: FragmentMoviesBinding,
    imageLoader: RequestManager
) : BaseScreen<MoviesViewEvent>() {

    private val moviesAdapter = MoviesAdapter(
        imageLoader,
        onItemBind = { movie -> _event.tryEmit(MoviesViewEvent.MovieBound(movie)) },
        onItemClick = { movie -> _event.tryEmit(MoviesViewEvent.MovieClicked(movie.id)) }
    )

    init {
        binding.toolbar.setupToolbarWithNavController()
        binding.movieList.adapter = moviesAdapter
    }

    fun render(state: MoviesViewState) {
        with(binding) {
            when (state) {
                is MoviesViewState.Idle -> {
                    progress.isVisible = false
                }
                is MoviesViewState.Loading -> {
                    progress.isVisible = true
                    moviesAdapter.submitList(emptyList())
                }
                is MoviesViewState.Result -> {
                    progress.isVisible = false
                    moviesAdapter.submitList(state.movies)
                }
                is MoviesViewState.EmptyResult -> {
                    progress.isVisible = false
                    moviesAdapter.submitList(emptyList())
                    // TODO Empty UI
                    root.showError(RuntimeException("No movies"))
                }
                is MoviesViewState.Error -> {
                    progress.isVisible = false
                    root.showError(state.error)
                }
            }
        }
    }

}

sealed class MoviesViewEvent {
    data class MovieBound(val movie: MovieModel) : MoviesViewEvent()
    data class MovieClicked(val movieId: String) : MoviesViewEvent()
}
