package com.sevenreup.albumsample.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.sevenreup.albumsample.R
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams


@Composable
fun ImageHolder(url: String, modifier: Modifier = Modifier) {
    com.skydoves.landscapist.glide.GlideImage(
        imageModel = url, modifier = modifier,
        circularReveal = CircularReveal(duration = 300),
        shimmerParams = ShimmerParams(
            baseColor = MaterialTheme.colors.background,
            highlightColor = Color.Gray.copy(alpha = 0.7F),
            dropOff = 0.65f
        ),
        requestOptions = {
            RequestOptions()
                .override(256, 256)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
        },
        failure = {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "image request failed.",
                    style = MaterialTheme.typography.body2
                )
            }
        },
        contentScale = ContentScale.Crop,
    )
}