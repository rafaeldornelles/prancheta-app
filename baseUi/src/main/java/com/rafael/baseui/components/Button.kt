package com.rafael.baseui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha

@Composable
fun Button(
    modifier: Modifier = Modifier,
    text: String,
    state: ButtonState = ButtonState(),
    onClick: () -> Unit,
) {
    androidx.compose.material.Button(
        modifier = modifier,
        enabled = state.enabled,
        onClick = onClick
    ) {
        Box(contentAlignment = Alignment.Center) {
            val contentAlpha by animateFloatAsState(targetValue = if (state.loading) 0f else 1f)
            if (state.loading) {
                CircularProgressIndicator()
            }
            Text(modifier = Modifier.alpha(contentAlpha), text = text)
        }
    }
}

data class ButtonState(
    val enabled: Boolean = true,
    val loading: Boolean = false
)