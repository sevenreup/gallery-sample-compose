package com.sevenreup.albumsample.ui.media

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.sevenreup.albumsample.data.model.MediaDTO
import com.sevenreup.albumsample.ui.components.ImageHolder
import com.sevenreup.albumsample.ui.main.MainViewModel
import com.sevenreup.albumsample.utils.getTimeFromDateString
import com.sevenreup.albumsample.utils.normaliseDateString

@Composable
fun MediaScreen(viewModel: MainViewModel, navController: NavController) {
    val selected by viewModel.selected.observeAsState(null)
    selected?.let {
        MediaContainer(it) {
            navController.popBackStack()
        }
    }
}

@Composable
private fun MediaContainer(media: MediaDTO, onBackPressed: () -> Unit) {
    Scaffold(topBar = {
        TopAppBar {
            IconButton(onClick = onBackPressed) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Column() {
                Text(
                    text = media.createdAt.normaliseDateString(),
                    style = MaterialTheme.typography.h6
                )
                Text(text = media.createdAt.getTimeFromDateString())
            }

        }
    }) {
        ImageHolder(
            url = media.downloadUrl,
            width = media.resx,
            height = media.resy,
            modifier = Modifier.padding(it)
        )
    }

}