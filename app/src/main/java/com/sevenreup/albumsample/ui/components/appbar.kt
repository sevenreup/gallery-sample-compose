package com.sevenreup.albumsample.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun GalleryAppBar(content: @Composable RowScope.() -> Unit) {
    val appBarColor = MaterialTheme.colors.surface.copy(alpha = 0.87f)

    TopAppBar(backgroundColor = appBarColor, elevation = 0.dp) {
        content()
    }
}