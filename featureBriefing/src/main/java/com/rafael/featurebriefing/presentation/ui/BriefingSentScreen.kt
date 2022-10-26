package com.rafael.featurebriefing.presentation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.runtime.Composable
import com.rafael.baseui.components.HelperAlert

@Composable
fun BriefingSentScreen() {
    HelperAlert(text = "Seu briefing foi enviado com sucesso!", vector = Icons.Outlined.Check)
}