package com.sevenreup.albumsample.ui.album

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.sevenreup.albumsample.data.model.MediaDTO
import com.sevenreup.albumsample.ui.components.ImageHolder
import com.sevenreup.albumsample.ui.main.MainViewModel
import com.sevenreup.albumsample.ui.settings.SettingsBottomSheet
import com.sevenreup.albumsample.utils.toMb
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun AlbumScreen(viewModel: MainViewModel, navController: NavController) {
    val mediaList by viewModel.mediaList.observeAsState(listOf())
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 0.dp,
        topBar = {
            TopAppBar {
                Text(text = "Sample Gallery", Modifier.weight(1F))
                IconButton(onClick = {
                    coroutineScope.launch {
                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        } else {
                            bottomSheetScaffoldState.bottomSheetState.collapse()
                        }
                    }
                }) {
                    Icon(Icons.Default.Info, contentDescription = "Settings")
                }
            }
        },
        sheetContent = {
            SettingsBottomSheet(viewModel)
        }) {
        AlbumGrid(mediaList, onRefresh = {

        }) {
            viewModel.selected.value = it
            navController.navigate("media")
        }
    }
}

@Composable
fun AlbumGrid(
    photos: List<MediaDTO>,
    onRefresh: () -> Unit,
    onItemClick: (photo: MediaDTO) -> Unit
) {
    val swipeRefreshState = rememberSwipeRefreshState(true)

    SwipeRefresh(state = swipeRefreshState, onRefresh = onRefresh) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(photos.size) { index ->
                val photo = photos[index]
                PhotoContainer(photo, onItemClick)
            }
        }
    }

}

@Composable
fun PhotoContainer(photo: MediaDTO, onClick: (photo: MediaDTO) -> Unit) {
    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .size(160.dp)
            .clickable {
                onClick(photo)
            },
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