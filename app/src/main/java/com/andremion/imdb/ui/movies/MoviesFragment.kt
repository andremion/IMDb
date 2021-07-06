package com.andremion.imdb.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.andremion.imdb.R
import com.andremion.imdb.databinding.FragmentMoviesBinding
import com.andremion.imdb.placeholder.PlaceholderContent
import com.andremion.imdb.ui.details.MovieDetailsFragment
import com.andremion.imdb.ui.movies.model.MovieModel
import com.andremion.imdb.util.setupToolbarWithNavController
import com.bumptech.glide.Glide

/**
 * A Fragment representing a list of Pings. This fragment
 * has different presentations for handset and larger screen devices. On
 * handsets, the fragment presents a list of items, which when touched,
 * lead to a {@link ItemDetailFragment} representing
 * item details. On larger screens, the Navigation controller presents the list of items and
 * item details side-by-side using two vertical panes.
 */

class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding: FragmentMoviesBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setupToolbarWithNavController()

        val recyclerView: RecyclerView = binding.movieList

        // Leaving this not using view binding as it relies on if the view is visible
        // based on the current layout configuration (layout, layout-sw600dp)
        val movieDetailsContainer: View? = view.findViewById(R.id.movie_details_nav_host_container)

        /** Trigger navigation based on if you have a single pane layout or two pane layout */
        val onItemClicked = { item: MovieModel ->
            val bundle = Bundle().apply {
                putString(MovieDetailsFragment.ARG_ITEM_ID, item.id)
            }
            if (movieDetailsContainer != null) {
                movieDetailsContainer.findNavController().navigate(R.id.movie_details_fragment, bundle)
            } else {
                recyclerView.findNavController().navigate(R.id.show_movie_detail, bundle)
            }
        }

        recyclerView.setupWith(onItemClicked)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun RecyclerView.setupWith(onItemClicked: (item: MovieModel) -> Unit) {
        val imageLoader = Glide.with(this)
        val movieListAdapter = MovieListAdapter(imageLoader, onItemClicked)
        adapter = movieListAdapter
        movieListAdapter.submitList(PlaceholderContent.ITEMS)
    }
}
