package com.rafael.baseui.components

import android.widget.Space
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.VerticalAlignmentLine
import com.rafael.baseui.theme.spacing

@Composable
fun SelectionItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    onSelectChange: (Boolean) -> Unit,
    alignment: Alignment.Vertical = Alignment.CenterVertically,
    content: @Composable () -> Unit
) {
    Row(modifier = modifier.fillMaxWidth().clickable { onSelectChange(isSelected.not()) }, verticalAlignment = alignment) {
        Checkbox(checked = isSelected, onCheckedChange = onSelectChange)
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.x300))
        Box(Modifier.weight(1f)) {
            content()
        }
    }
}