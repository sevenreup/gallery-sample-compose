package com.sevenreup.albumsample.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly

open class TextFieldState(
    value: String,
    private val validator: (String) -> Boolean = { true },
    private val errorFor: (input: String, title: String?) -> String = { _, _ -> "" }
) {
    var title: String? = null
    var text: String by mutableStateOf(value)

    // was the TextField ever focused
    var isFocusedDirty: Boolean by mutableStateOf(false)
    var isFocused: Boolean by mutableStateOf(false)
    private var displayErrors: Boolean by mutableStateOf(false)

    open val isValid: Boolean
        get() = validator(text)

    fun onFocusChange(focused: Boolean) {
        isFocused = focused
        if (focused) isFocusedDirty = true
    }

    fun enableShowErrors() {
        // only show errors if the text was at least once focused
        if (isFocusedDirty) {
            displayErrors = true
        }
    }

    fun showErrors() = !isValid && displayErrors

    open fun getError(): String? {
        return if (showErrors()) {
            errorFor(text, title)
        } else {
            null
        }
    }
}

class ShareIdInputState(value: String) : TextFieldState(value = value, validator = {
    it.length > 3
}, errorFor = { input, title ->
    "Invalid $title"
})

class NumericInputState(value: String) : TextFieldState(value = value,
    validator = { it.isDigitsOnly() },
    errorFor = { input, title -> "Invalid $title" })

class ModeInputState(value: String) :
    TextFieldState(value = value, validator = { true }, errorFor = { input, title -> "" })

