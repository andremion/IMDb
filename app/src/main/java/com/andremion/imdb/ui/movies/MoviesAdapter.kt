package com.andremion.imdb.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andremion.imdb.R
import com.andremion.imdb.databinding.FragmentMoviesItemBinding
import com.andremion.imdb.ui.movies.model.MovieModel
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

class MovieListAdapter(
    private val imageLoader: RequestManager,
    private val onItemClicked: (item: MovieModel) -> Unit
) : ListAdapter<MovieModel, MovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = FragmentMoviesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(imageLoader, binding) { position ->
            onItemClicked(getItem(position))
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(getItem(position))
}

class MovieViewHolder(
    private val imageLoader: RequestManager,
    private val binding: FragmentMoviesItemBinding,
    onItemClicked: (position: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.setOnClickListener { onItemClicked(bindingAdapterPosition) }
    }

    fun bind(item: MovieModel) {
        with(binding) {
            imageLoader.load(item.image)
                .placeholder(R.drawable.ic_movie_image_placeholder)
                .apply(RequestOptions.noTransformation())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(image)
            title.text = item.title
            year.text = item.year
        }
    }
}

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieModel>() {

    override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean =
        oldItem == newItem
}
