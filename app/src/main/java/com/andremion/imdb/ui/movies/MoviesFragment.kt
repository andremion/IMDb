package com.andremion.imdb.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.andremion.imdb.R
import com.andremion.imdb.di.ViewModelFactory
import com.andremion.imdb.ui.details.MovieDetailsFragment
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * A Fragment representing a list of Movies.
 * This fragment has different presentations for handset and larger screen devices.
 * On handsets, the fragment presents a list of items, which when touched,
 * lead to a [MovieDetailsFragment] representing item details.
 * On larger screens, the Navigation controller presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class MoviesFragment : Fragment() {

    @Inject
    lateinit var screen: MoviesScreen

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: MoviesViewModel by viewModels { viewModelFactory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_movies, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)

        viewModel.state
            .onEach(screen::render)
            .launchIn(viewLifecycleOwner.lifecycleScope)
        screen.event
            .onEach(::onEvent)
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun onEvent(event: MoviesViewEvent) {
        when (event) {
            is MoviesViewEvent.MovieBound -> viewModel.onMovieBound(event.movie)
            is MoviesViewEvent.MovieClicked -> {
                // Trigger navigation based on if you have a single pane layout or two pane layout.
                // Leaving this not using view binding as it relies on if the view is visible
                // based on the current layout configuration (layout, layout-sw600dp)
                val movieDetailsContainer: View? = view?.findViewById(R.id.movie_details_nav_host_container)
                val navController = movieDetailsContainer?.findNavController() ?: findNavController()

                val showMovieDetail = MoviesFragmentDirections.showMovieDetail(event.movieId)
                navController.navigate(showMovieDetail)
            }
        }
    }
}
