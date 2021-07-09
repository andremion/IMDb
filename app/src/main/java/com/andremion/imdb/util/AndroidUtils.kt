package com.andremion.imdb.util

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.andremion.imdb.R
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar

fun Toolbar.setupToolbarWithNavController() {
    val navController = findNavController()
    val appBarConfiguration = AppBarConfiguration(navController.graph)
    setupWithNavController(navController, appBarConfiguration)
}

fun RequestManager.loadImage(
    image: String,
    imageView: ImageView,
    withCrossFade: Boolean = true,
    onLoad: (() -> Unit)? = null
) {
    load(image)
        .placeholder(R.drawable.ic_movie_image_placeholder)
        .apply(RequestOptions.noTransformation())
        .mayCrossFade(withCrossFade)
        .addListener(requestListener(onLoad))
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

private fun requestListener(onLoad: (() -> Unit)?): RequestListener<Drawable> =
    object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            onLoad?.let { it() }
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            onLoad?.let { it() }
            return false
        }

    }

private fun RequestBuilder<Drawable>.mayCrossFade(withCrossFade: Boolean): RequestBuilder<Drawable> =
    if (withCrossFade) {
        transition(DrawableTransitionOptions.withCrossFade())
    } else {
        this
    }
