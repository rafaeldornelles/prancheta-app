package com.rafael.featurebriefing.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.rafael.baseui.components.TextField
import com.rafael.core.model.BriefingFormQuestion

@Composable
fun NumberQuestion(
    modifier: Modifier = Modifier.fillMaxWidth(),
    question: BriefingFormQuestion,
    onValueChange: (id: String, value: String) -> Unit
) {
    TextField(
        modifier = modifier,
        value = question.answer.orEmpty(),
        label = question.question.label,
        placeholder = question.question.placeholder,
        onValueChange = { onValueChange(question.question.id, it.filter { it.isDigit() }) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        trailingIcon = question.question.trailingText?.let {
            {
                Text(text = it)
            }
        }
    )
}