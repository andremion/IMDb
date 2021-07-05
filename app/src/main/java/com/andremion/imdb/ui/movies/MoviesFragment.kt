package com.andremion.imdb.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.andremion.imdb.R
import com.andremion.imdb.databinding.FragmentItemListBinding
import com.andremion.imdb.placeholder.PlaceholderContent
import com.andremion.imdb.ui.details.ItemDetailFragment
import com.andremion.imdb.ui.movies.model.MovieModel
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

    private var _binding: FragmentItemListBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.itemList

        // Leaving this not using view binding as it relies on if the view is visible
        // based on the current layout configuration (layout, layout-sw600dp)
        val itemDetailFragmentContainer: View? = view.findViewById(R.id.item_detail_nav_container)

        /** Click Listener to trigger navigation based on if you have
         * a single pane layout or two pane layout
         */
        val onClickListener = { item: MovieModel ->
            val bundle = Bundle().apply {
                putString(ItemDetailFragment.ARG_ITEM_ID, item.id)
            }
            if (itemDetailFragmentContainer != null) {
                itemDetailFragmentContainer.findNavController()
                    .navigate(R.id.fragment_item_detail, bundle)
            } else {
                recyclerView.findNavController().navigate(R.id.show_item_detail, bundle)
            }
        }

        setupRecyclerView(recyclerView, onClickListener)
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        onItemClicked: (item: MovieModel) -> Unit
    ) {
        val imageLoader = Glide.with(this)
        val movieListAdapter = MovieListAdapter(imageLoader, onItemClicked)
        recyclerView.adapter = movieListAdapter
        movieListAdapter.submitList(PlaceholderContent.ITEMS)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
