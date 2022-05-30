package com.sevenreup.albumsample.ui.album

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.sevenreup.albumsample.R
import com.sevenreup.albumsample.data.model.MediaDTO
import com.sevenreup.albumsample.ui.components.GalleryAppBar
import com.sevenreup.albumsample.ui.components.ImageHolder
import com.sevenreup.albumsample.ui.components.PageContainer
import com.sevenreup.albumsample.ui.main.MainViewModel
import com.sevenreup.albumsample.ui.settings.SettingsBottomSheet
import com.sevenreup.albumsample.utils.Response
import com.sevenreup.albumsample.utils.toMb
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun AlbumScreen(viewModel: MainViewModel, navController: NavController) {
    val mediaResponse by viewModel.mediaResponse.observeAsState(Response.Loading())
    val bottomState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    BackHandler(enabled = bottomState.isVisible) {
        coroutineScope.launch {
            bottomState.hide()
        }
    }

    PageContainer()
    {
        ModalBottomSheetLayout(
            sheetState = bottomState,
            sheetContent = {
                SettingsBottomSheet(viewModel)
            }) {
            Scaffold(
                topBar = {
                    GalleryAppBar {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.weight(1F)
                        )
                        IconButton(onClick = {
                            coroutineScope.launch {
                                if (bottomState.isVisible) {
                                    bottomState.hide()
                                } else {
                                    bottomState.show()
                                }
                            }
                        }) {
                            Icon(
                                Icons.Default.Settings,
                                contentDescription = stringResource(id = R.string.settings)
                            )
                        }
                    }
                },
                content = {
                    Box(Modifier.padding(it)) {
                        AlbumGrid(mediaResponse = mediaResponse, onItemClick = {
                            viewModel.selected.value = it
                            navController.navigate("media")
                        }, refresh = {
                            viewModel.refreshRefresh()
                        })
                    }
                },
            )
        }
    }

}

@Composable
fun AlbumGrid(
    mediaResponse: Response<List<MediaDTO>?>,
    onItemClick: (photo: MediaDTO) -> Unit,
    refresh: () -> Unit
) {
    val swipeRefreshState = rememberSwipeRefreshState(mediaResponse !is Response.Loading)

    SwipeRefresh(state = swipeRefreshState, onRefresh = refresh) {
        when (mediaResponse) {
            is Response.Failure -> {
                swipeRefreshState.isRefreshing = false
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.Warning, contentDescription = "Error")
                    Text(text = stringResource(id = R.string.something_went_wrong))
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = mediaResponse.message ?: "")
                    Button(onClick = refresh) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = stringResource(id = R.string.refresh)
                        )
                        Text(text = stringResource(id = R.string.refresh))
                    }
                }
            }
            is Response.Success -> {
                swipeRefreshState.isRefreshing = false
                val media = mediaResponse.data!!
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(media.size) { index ->
                        val photo = media[index]
                        PhotoContainer(photo, onItemClick)
                    }
                }
            }
            else -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Text(text = "Loading")
                }

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
                url = photo.thumbnailUrl,
                height = 160,
                width = 160,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.medium)
            )
            Box(
                modifier = Modifier
                    .padding(bottom = 2.dp, end = 2.dp)
                    .background(MaterialTheme.colors.secondary, shape = MaterialTheme.shapes.small)
                    .align(Alignment.BottomEnd)
            ) {
                Text(
                    text = photo.size.toMb(),
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.caption.copy(color = MaterialTheme.colors.onSecondary)
                )
            }
        }
    }

}