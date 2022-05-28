package com.sevenreup.albumsample.ui.album

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sevenreup.albumsample.data.model.MediaDTO
import com.sevenreup.albumsample.ui.components.ImageHolder
import com.sevenreup.albumsample.ui.main.MainViewModel
import com.sevenreup.albumsample.utils.toMb

@Composable
fun AlbumScreen(viewModel: MainViewModel) {
    val mediaList by viewModel.mediaList.observeAsState(listOf())

    AlbumGrid(mediaList)
}

@Composable
fun AlbumGrid(photos: List<MediaDTO>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(photos.size) { index ->
            val photo = photos[index]
            PhotoContainer(photo)
        }
    }
}

@Composable
fun PhotoContainer(photo: MediaDTO) {
    Card(
        Modifier
            .aspectRatio(1f)
            .size(160.dp)
    ) {
        Box() {
            ImageHolder(
                url = photo.thumbnailUrl, modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.medium)
            )
            Box(
                modifier = Modifier
                    .background(Color.Cyan)
                    .align(Alignment.BottomEnd)
            ) {
                Text(text = photo.size.toMb())
            }
        }
    }

}