package com.sevenreup.albumsample.ui.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sevenreup.albumsample.ui.album.AlbumScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(
        navController,
        startDestination = "home"
    ) {
        composable("home") {
            AlbumScreen(viewModel = hiltViewModel())
        }
        composable("library") {
            Text(text = "I am the livr")
        }
    }
}