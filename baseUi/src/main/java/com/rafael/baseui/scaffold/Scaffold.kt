package com.rafael.baseui.scaffold

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import com.rafael.baseui.R
import com.rafael.baseui.common.UiState
import com.rafael.baseui.theme.spacing

@Composable
fun <T> Scaffold(
    state: UiState<T>,
    topBar: @Composable () -> Unit = { TopAppBar() },
    loading: @Composable () -> Unit = { DefaultLoading() },
    error: @Composable (t: Throwable) -> Unit = { DefaultError(it) },
    bottomBar: @Composable () -> Unit = {},
    success: @Composable (T) -> Unit
) {
    androidx.compose.material.Scaffold(
        topBar = topBar,
        bottomBar = bottomBar
    ) { paddingValues ->
        val layoutDirection = LocalLayoutDirection.current
        val successAlpha by animateFloatAsState(targetValue = if (state is UiState.Success) 1f else 0f)
        val loadingAlpha by animateFloatAsState(targetValue = if (state is UiState.Loading) 1f else 0f)
        val errorAlpha by animateFloatAsState(targetValue = if (state is UiState.Error) 1f else 0f)
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                    start = paddingValues.calculateStartPadding(layoutDirection),
                    end = paddingValues.calculateEndPadding(layoutDirection)
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(successAlpha)
                    .padding(horizontal = MaterialTheme.spacing.x300)
            ) {
                state.getOrNull()?.let { success(it) }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(loadingAlpha)
            ) {
                loading()
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(errorAlpha)
            ) {
                state.errorOrNull()?.let { error(it) }
            }
        }
    }
}

@Composable
fun DefaultLoading() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.x300),
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun DefaultError(t: Throwable) {
    Text(text = stringResource(id = R.string.default_error_message, t.message.orEmpty()))
}