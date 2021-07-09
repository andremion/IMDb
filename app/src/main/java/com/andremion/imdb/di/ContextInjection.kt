package com.andremion.imdb.di

import android.content.Context
import dagger.android.HasAndroidInjector

fun Context.inject(target: Any) {
    findAndroidInjector()
        .androidInjector()
        .inject(target)
}

private fun Context.findAndroidInjector(): HasAndroidInjector =
    requireNotNull(applicationContext as? HasAndroidInjector) {
        "No injector was found in ${javaClass.canonicalName}"
    }
