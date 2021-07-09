package com.andremion.imdb.ui.movies

import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.andremion.imdb.data.MoviesRepository
import com.andremion.imdb.data.worker.FetchMovieDetailsWorker
import com.andremion.imdb.domain.Movie
import com.andremion.imdb.ui.movies.mapper.MoviesModelMapper
import com.andremion.imdb.ui.movies.model.MovieModel
import com.andremion.imdb.util.mvvm.BaseViewModel
import com.andremion.imdb.util.transform
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class MoviesViewModel @Inject constructor(
    private val workManager: WorkManager,
    private val moviesRepository: MoviesRepository,
    private val modelMapper: MoviesModelMapper
) : BaseViewModel<MoviesViewState>(MoviesViewState.Idle) {

    init {
        init()
    }

    private fun init() {
        moviesRepository.getMostPopularMovies()
            .onStart { _state.value = MoviesViewState.Loading }
            .onEach { movies -> _state.value = onResult(movies) }
            .catch { throwable -> _state.value = MoviesViewState.Error(throwable) }
            .launchIn(viewModelScope)
    }

    private fun onResult(movies: List<Movie>): MoviesViewState =
        if (movies.isEmpty()) {
            MoviesViewState.EmptyResult
        } else {
            modelMapper.map(movies)
                .transform(MoviesViewState::Result)
        }

    fun onMovieBound(movie: MovieModel) {
        if (movie.isLoading) {
            val workRequest = FetchMovieDetailsWorker.createWorkRequest(movie.id)
            workManager.enqueue(workRequest)
        }
    }
}

sealed class MoviesViewState {
    object Idle : MoviesViewState()
    object Loading : MoviesViewState()
    data class Result(val movies: List<MovieModel>) : MoviesViewState()
    object EmptyResult : MoviesViewState()
    data class Error(val error: Throwable) : MoviesViewState()
}
