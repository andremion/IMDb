package com.andremion.imdb.util

import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.andremion.imdb.R
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar

fun Toolbar.setupToolbarWithNavController() {
    val navController = findNavController()
    val appBarConfiguration = AppBarConfiguration(navController.graph)
    setupWithNavController(navController, appBarConfiguration)
}

fun RequestManager.loadImage(image: String, imageView: ImageView) {
    load(image)
        .placeholder(R.drawable.ic_movie_image_placeholder)
        .apply(RequestOptions.noTransformation())
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(imageView)
}

fun View.showError(error: Throwable) {
    val text = if (error.message.isNullOrBlank()) {
        context.getString(R.string.generic_error)
    } else {
        error.message!!
    }
    Snackbar.make(this, text, Snackbar.LENGTH_LONG).show()
}
