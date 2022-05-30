package com.sevenreup.albumsample.ui.main

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.sevenreup.albumsample.ui.album.AlbumScreen
import com.sevenreup.albumsample.ui.media.MediaScreen

@ExperimentalMaterialApi
@ExperimentalMaterialNavigationApi
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val viewModel: MainViewModel = hiltViewModel()

    NavHost(
        navController,
        startDestination = "home"
    ) {
        composable("home") {
            AlbumScreen(viewModel = viewModel, navController)
        }
        composable("media") {
            MediaScreen(viewModel = viewModel, navController = navController)
        }
    }
}