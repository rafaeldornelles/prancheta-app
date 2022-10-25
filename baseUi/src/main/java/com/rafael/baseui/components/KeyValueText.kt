package com.rafael.baseui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

@Composable
fun KeyValueText(
    modifier: Modifier = Modifier,
    key: String,
    value: String,
    style: TextStyle = LocalTextStyle.current
) {
    val text = buildAnnotatedString {
        withStyle(style.copy(fontWeight = FontWeight.Bold).toSpanStyle()) {
            append(key)
            append(": ")
        }
        withStyle(style.toSpanStyle()) {
            append(value)
        }
    }
    Text(text = text, modifier = modifier)
}