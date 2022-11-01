package com.rafael.featurebriefing.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import coil.compose.SubcomposeAsyncImage
import com.rafael.baseui.components.RadioItem
import com.rafael.baseui.theme.spacing
import com.rafael.core.model.BriefingFormQuestion

@Composable
fun RadioImageQuestion(
    modifier: Modifier = Modifier.fillMaxWidth(),
    question: BriefingFormQuestion,
    onValueChange: (id: String, value: String) -> Unit
) {
    Column(modifier) {
        Text(text = question.question.label, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.x200))
        question.question.options?.forEachIndexed { i, option ->
            RadioItem(
                isSelected = question.answer == option,
                alignment = Alignment.Top,
                value = option,
                onSelectChange = { onValueChange(question.question.id, it) }
            ) {
                Column() {
                    SubcomposeAsyncImage(
                        model = question.question.optionsUrl?.get(i),
                        contentDescription = option,
                        loading = { CircularProgressIndicator() }
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = option,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.x300))
        }
    }
}