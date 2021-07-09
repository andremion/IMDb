package com.andremion.imdb.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.andremion.imdb.R
import com.andremion.imdb.di.ViewModelFactory
import com.andremion.imdb.ui.movies.MoviesFragment
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a [MoviesFragment]
 * in two-pane mode (on larger screen devices) or self-contained on handsets.
 */
class MovieDetailsFragment : Fragment() {

    @Inject
    lateinit var screen: MovieDetailsScreen

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: MovieDetailsViewModel by viewModels { viewModelFactory }
    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
        setupTransition()
    }

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

        viewModel.init(args.movieId)
    }

    private fun setupTransition() {
        val inflater = TransitionInflater.from(requireContext())
        sharedElementEnterTransition = inflater.inflateTransition(android.R.transition.move)
    }

    private fun onEvent(event: MovieDetailsViewEvent) {
        when (event) {
            MovieDetailsViewEvent.ResultBound -> startPostponedEnterTransition()
        }
    }
}
