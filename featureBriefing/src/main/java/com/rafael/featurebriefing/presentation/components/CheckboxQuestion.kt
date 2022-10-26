package com.rafael.featurebriefing.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.rafael.baseui.components.SelectionItem
import com.rafael.featurebriefing.domain.entity.BriefingFormQuestion

@Composable
fun CheckboxQuestion(
    modifier: Modifier = Modifier.fillMaxWidth(),
    question: BriefingFormQuestion,
    onValueChange: (id: String, value: String) -> Unit
) {
    Column(modifier = modifier) {
        Text(text = question.question.label, fontWeight = FontWeight.Bold)
        val itemsSelected = question.answer?.split("|")?.toMutableSet() ?: mutableSetOf()
        question.question.options?.forEach { option ->
            SelectionItem(isSelected = option in itemsSelected, onSelectChange = {
                if (!itemsSelected.add(option)) itemsSelected.remove(option)
                onValueChange(question.question.id, itemsSelected.joinToString("|"))
            }) {
                Text(option)
            }
        }
    }
}