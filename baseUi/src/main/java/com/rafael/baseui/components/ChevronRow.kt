package com.rafael.baseui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.rafael.baseui.theme.spacing

@Composable
fun ChevronRow(
    modifier: Modifier = Modifier,
    internalPadding: Dp = MaterialTheme.spacing.x300,
    onclick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val rowModifier = if (onclick != null) modifier.clickable {
        onclick()
    }.padding(internalPadding) else modifier.padding(internalPadding)

    Row(rowModifier, verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            content()
        }
        Icon(imageVector = Icons.Outlined.ChevronRight, contentDescription = null)
    }
}