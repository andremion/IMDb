package com.andremion.imdb.util

/**
 * Transforms/maps an object resulted of applying the given [block] function.
 * Do exactly the same as [let] does but uses a better semantic name for transformations.
 */
inline fun <T, R> T.transform(block: (T) -> R): R = block(this)
