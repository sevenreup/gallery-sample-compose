package com.sevenreup.albumsample.ui.main

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.sevenreup.albumsample.ui.album.AlbumScreen
import com.sevenreup.albumsample.ui.media.MediaScreen

@ExperimentalMaterialApi
@ExperimentalMaterialNavigationApi
@Composable
fun MainScreen() {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)
    val viewModel: MainViewModel = hiltViewModel()

    ModalBottomSheetLayout(bottomSheetNavigator) {
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
}