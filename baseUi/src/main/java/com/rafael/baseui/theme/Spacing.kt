package com.rafael.baseui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Spacing(
    val x300: Dp
) {
    companion object {
        val default = Spacing(
            x300 = 16.dp
        )
    }
}

val LocalSpacing = compositionLocalOf {
    Spacing.default
}

val MaterialTheme.spacing : Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current