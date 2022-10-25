package com.rafael.baseui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rafael.baseui.theme.spacing

@Composable
fun Card(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    androidx.compose.material.Card(
        modifier = modifier.padding(vertical = MaterialTheme.spacing.x200),
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.primary.copy(.1f)
    ) {
        content()
    }
}