package com.rafael.baseui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.rafael.baseui.theme.spacing

@Composable
fun KeyValueRow(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(MaterialTheme.spacing.x300),
    key: String,
    value: String
) {
    Column {
        Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(modifier = Modifier.weight(1f), text = key, fontWeight = FontWeight.Medium)
            Text(modifier = Modifier.weight(1f), text = value)
        }
        Divider()
    }
}