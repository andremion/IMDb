package com.andremion.imdb.ui.details

import androidx.lifecycle.viewModelScope
import com.andremion.imdb.data.MoviesRepository
import com.andremion.imdb.ui.details.mapper.MovieDetailsModelMapper
import com.andremion.imdb.ui.details.model.MovieDetailsModel
import com.andremion.imdb.util.mvvm.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val modelMapper: MovieDetailsModelMapper
) : BaseViewModel<MovieDetailsViewState>(MovieDetailsViewState.Idle) {

    fun init(movieId: String) {
        _state.value = MovieDetailsViewState.Loading
        viewModelScope.launch {
            _state.value = try {
                val movieDetails = moviesRepository.getMovieDetails(movieId)
                val model = modelMapper.map(movieDetails)
                MovieDetailsViewState.Result(model)
            } catch (e: Throwable) {
                MovieDetailsViewState.Error(e)
            }
        }
    }
}

sealed class MovieDetailsViewState {
    object Idle : MovieDetailsViewState()
    object Loading : MovieDetailsViewState()
    data class Result(val movieDetails: MovieDetailsModel) : MovieDetailsViewState()
    data class Error(val error: Throwable) : MovieDetailsViewState()
}
