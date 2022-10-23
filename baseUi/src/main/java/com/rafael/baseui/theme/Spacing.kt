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
    val x100: Dp,
    val x200: Dp,
    val x300: Dp,
    val x400: Dp,
    val x500: Dp,
    val x600: Dp,
    val x700: Dp
) {
    companion object {
        val default = Spacing(
            x100 = 4.dp,
            x200 = 8.dp,
            x300 = 16.dp,
            x400 = 24.dp,
            x500 = 40.dp,
            x600 = 64.dp,
            x700 = 104.dp
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