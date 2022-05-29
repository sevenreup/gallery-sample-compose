package com.sevenreup.albumsample.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.sevenreup.albumsample.R
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams


@Composable
fun ImageHolder(url: String, height: Int, width: Int, modifier: Modifier = Modifier) {
    com.skydoves.landscapist.glide.GlideImage(
        imageModel = url,
        modifier = modifier,
        circularReveal = CircularReveal(duration = 300),
        shimmerParams = ShimmerParams(
            baseColor = MaterialTheme.colors.background,
            highlightColor = Color.Gray.copy(alpha = 0.7F),
            dropOff = 0.65f
        ),
        requestOptions = {
            RequestOptions()
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
        },
        failure = {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(Icons.Default.Warning, contentDescription = "Error")
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.image_request_failed),
                    style = MaterialTheme.typography.body2
                )
            }
        },
        contentScale = ContentScale.Crop,
    )
}