package com.rafael.baseui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.rafael.baseui.theme.spacing

@Composable
fun RadioItem(
    isSelected: Boolean,
    value: String,
    onSelectChange: (value: String) -> Unit,
    alignment: Alignment.Vertical = Alignment.CenterVertically,
    content: @Composable () -> Unit
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onSelectChange(value) },
        verticalAlignment = alignment
    ) {
        RadioButton(selected = isSelected, onClick = { onSelectChange(value) })
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.x300))
        content()
    }
}