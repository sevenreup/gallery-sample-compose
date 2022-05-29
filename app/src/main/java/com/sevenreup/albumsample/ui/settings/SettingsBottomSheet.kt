package com.sevenreup.albumsample.ui.settings

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sevenreup.albumsample.AppPreferences
import com.sevenreup.albumsample.R
import com.sevenreup.albumsample.RequestMessageOptions
import com.sevenreup.albumsample.ui.components.GenericAppInput
import com.sevenreup.albumsample.ui.components.MaterialSelect
import com.sevenreup.albumsample.ui.main.MainViewModel
import com.sevenreup.albumsample.utils.*

@ExperimentalMaterialApi
@Composable
fun SettingsBottomSheet(viewModel: MainViewModel) {
    val prefs by viewModel.prefs.observeAsState()
    val savingEdits by viewModel.savingEdits.observeAsState(Response.Idle())
    val context = LocalContext.current
    if (savingEdits is Response.Success) {
        Toast.makeText(
            LocalContext.current,
            stringResource(id = R.string.saving_success),
            Toast.LENGTH_SHORT
        ).show()
    }

    SettingsContent(
        preferences = prefs,
        onCacheClear = {
            viewModel.clearCache(context = context)
        },
        saving = savingEdits is Response.Loading,
        onPrefsEdit = { shareId, options ->
            viewModel.editPreferences(shareID = shareId, options = options)
        })
}

@ExperimentalMaterialApi
@Composable
private fun SettingsContent(
    preferences: AppPreferences?,
    onCacheClear: () -> Unit,
    saving: Boolean,
    onPrefsEdit: (String, RequestMessageOptions) -> Unit
) {
    Column(Modifier.padding(horizontal = 8.dp, vertical = 12.dp)) {
        Text(text = "General")
        Button(onClick = { /*TODO*/ }, Modifier.fillMaxWidth()) {
            Text(text = "Clear Cache")
        }
        if (preferences != null)
            RequestOptionEditForm(
                appPreferences = preferences,
                onSave = onPrefsEdit,
                saving = saving
            )
    }
}

@ExperimentalMaterialApi
@Composable
fun RequestOptionEditForm(
    appPreferences: AppPreferences,
    saving: Boolean,
    onSave: (String, RequestMessageOptions) -> Unit
) {
    val shareIdState = remember { ShareIdInputState(appPreferences.shareId) }
    val imageWidthState = remember { NumericInputState(appPreferences.options.width.toString()) }
    val imageHeightState = remember { NumericInputState(appPreferences.options.height.toString()) }
    val imageModeState = remember { ModeInputState(appPreferences.options.mode) }

    val options = listOf("bb", "crop", "md")

    Column(Modifier.padding(vertical = 16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Request Option")
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.LightGray)
                    .padding(horizontal = 8.dp)
            )
        }

        GenericAppInput(
            state = shareIdState,
            title = stringResource(id = R.string.shareId),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )

        GenericAppInput(
            state = imageWidthState,
            title = stringResource(id = R.string.width),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number
            )
        )

        GenericAppInput(
            state = imageHeightState,
            title = stringResource(id = R.string.height),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number
            )
        )

        MaterialSelect(
            state = imageModeState,
            label = stringResource(id = R.string.mode),
            options = options
        )

        Box(modifier = Modifier.height(3.dp))

        Button(
            onClick = {
                val opts = appPreferences.options.toBuilder()
                    .setHeight(imageHeightState.text.toInt())
                    .setWidth(imageWidthState.text.toInt())
                    .setMode(imageModeState.text)
                    .build()
                onSave(shareIdState.text, opts)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = shareIdState.isValid && imageHeightState.isValid && imageWidthState.isValid && imageModeState.isValid
        ) {
            if (saving) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
            }

            Text(text = stringResource(id = R.string.edit_share_id))
        }
    }
}