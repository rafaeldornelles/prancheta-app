package com.rafael.featurebriefing.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.rafael.baseui.components.RadioItem
import com.rafael.core.model.BriefingFormQuestion

@Composable
fun RadioQuestion(
    modifier: Modifier = Modifier.fillMaxWidth(),
    question: BriefingFormQuestion,
    onValueChange: (id: String, value: String) -> Unit
) {
    Column(modifier = modifier) {
        Text(text = question.question.label, fontWeight = FontWeight.Bold)
        question.question.options?.forEach {
            RadioItem(
                isSelected = question.answer == it,
                value = it,
                onSelectChange = { onValueChange(question.question.id, it) }
            ) {
                Text(text = it)
            }
        }
    }
}