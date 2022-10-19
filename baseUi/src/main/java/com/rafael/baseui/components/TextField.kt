package com.rafael.baseui.components

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rafael.baseui.R
import com.rafael.baseui.common.UiConstants.DEFAULT_DEBOUNCE
import com.rafael.baseui.theme.spacing
import kotlinx.coroutines.delay

@Composable
fun TextField(
    modifier: Modifier = Modifier.fillMaxWidth(),
    state: TextFieldState,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onValueChange: (String) -> Unit,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null
) {
    TextField(
        modifier = modifier,
        value = state.value,
        label = stringResource(id = state.label),
        placeholder = stringResource(id = state.placeholder),
        validator = state.validator,
        errorDebounce = state.errorDebounce,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        onValueChange = onValueChange,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon
    )
}

@Composable
fun TextField(
    modifier: Modifier = Modifier.fillMaxWidth(),
    value: String,
    label: String,
    placeholder: String?,
    validator: (@StringRes (String) -> Int?)? = null,
    errorDebounce: Long = DEFAULT_DEBOUNCE,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onValueChange: (String) -> Unit,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null
) {
    var debouncedError by remember {
        mutableStateOf(if (value.isNotEmpty()) validator?.invoke(value) else null)
    }

    LaunchedEffect(value) {
        delay(errorDebounce)
        if (value.isNotEmpty()) {
            debouncedError = validator?.invoke(value)
        }
    }

    Column {
        OutlinedTextField(
            modifier = modifier,
            value = value,
            label = {
                Text(text = label)
            },
            placeholder = {
                Text(text = placeholder.orEmpty())
            },
            isError = debouncedError != null,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            onValueChange = {
                debouncedError = null
                onValueChange(it.replace("\n", ""))
            },
            singleLine = true,
            maxLines = 1,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon
        )
        AnimatedVisibility(debouncedError != null) {
            Text(
                text = debouncedError?.let { stringResource(id = it) } ?: ""
            )
        }
    }
}

data class TextFieldState(
    val value: String = "",
    @StringRes val label: Int,
    @StringRes val placeholder: Int,
    val validator: (@StringRes (String) -> Int?)? = null,
    val errorDebounce: Long = DEFAULT_DEBOUNCE
) {
    fun withValue(value: String) = copy(value = value)
}

@Composable
@Preview
private fun TextFieldPreview() {
    TextField(
        state = TextFieldState(
            value = "",
            label = R.string.default_email,
            placeholder = R.string.default_password
        ),
        onValueChange = {}
    )
}