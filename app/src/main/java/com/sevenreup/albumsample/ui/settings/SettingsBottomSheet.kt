package com.sevenreup.albumsample.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SettingsBottomSheet() {
    Column(Modifier.padding(horizontal = 8.dp, vertical = 12.dp)) {
        Button(onClick = { /*TODO*/ }, Modifier.fillMaxWidth()) {
            Text(text = "Clear Cache")
        }
        Box(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.LightGray))
    }
}