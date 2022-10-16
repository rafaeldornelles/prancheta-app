package com.rafael.baseui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp

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
        elevation = ButtonDefaults.elevation(0.dp),
        onClick = onClick
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.heightIn(32.dp)) {
            val contentAlpha by animateFloatAsState(targetValue = if (state.loading) 0f else 1f)
            if (state.loading) {
                CircularProgressIndicator(color = MaterialTheme.colors.onPrimary)
            }
            Text(
                modifier = Modifier.alpha(contentAlpha), text = text
            )
        }
    }
}

data class ButtonState(
    val enabled: Boolean = true,
    val loading: Boolean = false
)