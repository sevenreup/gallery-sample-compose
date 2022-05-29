package com.sevenreup.albumsample.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sevenreup.albumsample.utils.TextFieldState

@Composable
fun GenericAppInput(
    state: TextFieldState,
    title: String,
    keyboardOptions: KeyboardOptions,
    onImeAction: () -> Unit = {}
) {
    state.title = title
    OutlinedTextField(
        value = state.text,
        onValueChange = { text ->
            if (keyboardOptions.keyboardType == KeyboardType.Number) {
                state.text = text.filter { it.isDigit() }
            } else {
                state.text = text
            }
        },
        label = {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.body2
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                state.onFocusChange(focusState.isFocused)
                if (!focusState.isFocused) {
                    state.enableShowErrors()
                }
            },
        textStyle = MaterialTheme.typography.body2,
        isError = state.showErrors(),
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onDone = {
                onImeAction()
            }
        ),
    )

    state.getError()?.let { error -> TextFieldError(textError = error) }
}

@ExperimentalMaterialApi
@Composable
fun MaterialSelect(
    state: TextFieldState,
    label: String,
    options: List<String>
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(state.text.ifBlank { options[0] }) }

    state.title = label

    ExposedDropdownMenuBox(expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
            state.onFocusChange(expanded)
        }) {
        OutlinedTextField(
            readOnly = true,
            value = selectedOptionText,
            onValueChange = { },
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            isError = state.showErrors(),
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )

        state.getError()?.let { error -> TextFieldError(textError = error) }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        selectedOptionText = selectionOption
                        state.text = selectedOptionText
                        expanded = false
                    }
                ) {
                    Text(text = selectionOption)
                }
            }
        }
    }
}

@Composable
fun TextFieldError(textError: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = textError,
            modifier = Modifier.fillMaxWidth(),
            style = LocalTextStyle.current.copy(color = MaterialTheme.colors.error)
        )
    }
}
