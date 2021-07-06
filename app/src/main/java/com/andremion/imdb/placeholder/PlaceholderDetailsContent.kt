package com.andremion.imdb.placeholder

import com.andremion.imdb.ui.details.model.MovieDetailsModel
import java.util.*

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object PlaceholderDetailsContent {

    /**
     * An array of sample (placeholder) items.
     */
    val ITEMS: MutableList<MovieDetailsModel> = ArrayList()

    /**
     * A map of sample (placeholder) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, MovieDetailsModel> = HashMap()

    private const val COUNT = 25

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createPlaceholderItem(i))
        }
    }

    private fun addItem(item: MovieDetailsModel) {
        ITEMS.add(item)
        ITEM_MAP[item.id] = item
    }

    private fun createPlaceholderItem(position: Int): MovieDetailsModel {
        return MovieDetailsModel(
            id = position.toString(),
            image = "https://m.media-amazon.com/images/M/MV5BNGM3YzdlOWYtNjViZS00MTE2LWE1MWUtZmE2ZTcxZjcyMmU3XkEyXkFqcGdeQXVyODEyMTI1MjA@._V1_.jpg",
            title = "Item: $position",
            year = (position + 2000).toString(),
            rating = "7.5",
            runtime = "1h 59m",
            outline = makeDetails(position),
            summary = makeDetails(position),
            genres = makeGenres(position),
        )
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0 until position) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }

    private fun makeGenres(position: Int): List<String> {
        val list = mutableListOf<String>()
        for (i in 0 until position) {
            list.add("genre")
        }
        return list
    }
}
