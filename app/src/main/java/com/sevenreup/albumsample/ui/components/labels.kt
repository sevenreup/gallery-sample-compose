package com.sevenreup.albumsample.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SettingsLabel(title: String) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(text = title, style = MaterialTheme.typography.subtitle1)
        Box(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(start = 8.dp, end = 8.dp)
                .background(Color.LightGray)
        )
    }
}