package com.andremion.imdb.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.andremion.imdb.R
import com.andremion.imdb.databinding.FragmentMovieDetailsBinding
import com.andremion.imdb.placeholder.PlaceholderDetailsContent
import com.andremion.imdb.ui.details.model.MovieDetailsModel
import com.andremion.imdb.util.loadImage
import com.andremion.imdb.util.setupToolbarWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [MoviesFragment]
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
class MovieDetailsFragment : Fragment() {

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }

    /**
     * The placeholder content this fragment is presenting.
     */
    private var item: MovieDetailsModel? = null

    private var _binding: FragmentMovieDetailsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding: FragmentMovieDetailsBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the placeholder content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                item = PlaceholderDetailsContent.ITEM_MAP[it.getString(ARG_ITEM_ID)]
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            toolbar?.setupToolbarWithNavController()
            item?.let {
                content.title.text = it.title
                content.year.text = it.year
                content.rating.text = it.rating
                content.runtime.text = it.runtime
                content.summary.text = it.summary
                val imageLoader = Glide.with(this@MovieDetailsFragment)
                imageLoader.loadImage(it.image, imageLarge)
                imageLoader.loadImage(it.image, content.imagePoster)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
