package com.andremion.imdb.ui.transition

import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.andremion.imdb.databinding.FragmentMovieDetailsBinding
import com.andremion.imdb.databinding.FragmentMoviesItemBinding
import com.andremion.imdb.ui.details.model.MovieDetailsModel
import com.andremion.imdb.ui.movies.model.MovieModel

fun FragmentMoviesItemBinding.toFragmentNavigatorExtras(): FragmentNavigator.Extras =
    FragmentNavigatorExtras(
        cardview to cardview.transitionName,
        image to image.transitionName,
        title to title.transitionName,
        year to year.transitionName
    )

fun FragmentMoviesItemBinding.setupSharedTransitionNames(movie: MovieModel) {
    cardview.transitionName = movie.id
    image.transitionName = movie.image
    title.transitionName = movie.title
    year.transitionName = "${movie.title}[${movie.year}]"
}

fun FragmentMovieDetailsBinding.setupSharedTransitionNames(movieDetails: MovieDetailsModel) {
    content.cardMain.transitionName = movieDetails.id
    content.imagePoster.transitionName = movieDetails.image
    content.title.transitionName = movieDetails.title
    content.year.transitionName = "${movieDetails.title}[${movieDetails.year}]"
}
