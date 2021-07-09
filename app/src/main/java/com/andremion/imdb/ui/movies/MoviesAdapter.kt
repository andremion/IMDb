package com.andremion.imdb.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andremion.imdb.databinding.FragmentMoviesItemBinding
import com.andremion.imdb.ui.movies.model.MovieModel
import com.andremion.imdb.util.loadImage
import com.bumptech.glide.RequestManager

class MoviesAdapter(
    private val imageLoader: RequestManager,
    private val onItemBind: (movie: MovieModel) -> Unit,
    private val onItemClick: (movie: MovieModel) -> Unit
) : ListAdapter<MovieModel, MovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = FragmentMoviesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(imageLoader, binding) { position ->
            onItemClick(getItem(position))
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
            .also { onItemBind(movie) }
    }
}

class MovieViewHolder(
    private val imageLoader: RequestManager,
    private val binding: FragmentMoviesItemBinding,
    onItemClick: (position: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.setOnClickListener { onItemClick(bindingAdapterPosition) }
    }

    fun bind(movie: MovieModel) {
        with(binding) {
            root.isEnabled = movie.isEnabled
            progress.isVisible = movie.isLoading
            imageLoader.loadImage(movie.image, image)
            title.text = movie.title
            year.text = movie.year
        }
    }
}

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieModel>() {

    override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean =
        oldItem == newItem
}
