package com.andremion.imdb.util

import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController

fun Toolbar.setupToolbarWithNavController() {
    val navController = findNavController()
    val appBarConfiguration = AppBarConfiguration(navController.graph)
    setupWithNavController(navController, appBarConfiguration)
}
