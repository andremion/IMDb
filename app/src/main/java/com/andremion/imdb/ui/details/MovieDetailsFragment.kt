package com.andremion.imdb.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.andremion.imdb.R
import com.andremion.imdb.di.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [MoviesFragment]
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
class MovieDetailsFragment : Fragment() {

    companion object {
        const val ARG_MOVIE_ID = "movie_id"
    }

    @Inject
    lateinit var screen: MovieDetailsScreen

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: MovieDetailsViewModel by viewModels { viewModelFactory }

    private val movieId: String
        get() = requireNotNull(arguments?.getString(ARG_MOVIE_ID)) { "Movie id argument is required" }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_movie_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)

        viewModel.state
            .onEach(screen::render)
            .launchIn(viewLifecycleOwner.lifecycleScope)
        screen.event
            .onEach(::onEvent)
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.init(movieId)
    }

    private fun onEvent(event: MovieDetailsViewEvent) {
        // TODO: Implement when there is event in this UI
    }
}
