package com.andremion.imdb.ui.movies

import androidx.lifecycle.viewModelScope
import com.andremion.imdb.data.MoviesRepository
import com.andremion.imdb.placeholder.PlaceholderContent
import com.andremion.imdb.ui.movies.mapper.MoviesModelMapper
import com.andremion.imdb.ui.movies.model.MovieModel
import com.andremion.imdb.util.mvvm.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val modelMapper: MoviesModelMapper
) : BaseViewModel<MoviesViewState>(MoviesViewState.Idle) {

    fun init() {
        _state.value = MoviesViewState.Loading
        viewModelScope.launch {
            _state.value = try {
                delay(2000)
//                val movies = moviesRepository.getMostPopularMovies()
//                if (movies.isEmpty()) {
//                    MoviesViewState.EmptyResult
//                } else {
//                    val models = modelMapper.map(movies)
//                    MoviesViewState.Result(models)
                MoviesViewState.Result(PlaceholderContent.ITEMS)
//                }
            } catch (e: Throwable) {
                MoviesViewState.Error(e)
            }
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
