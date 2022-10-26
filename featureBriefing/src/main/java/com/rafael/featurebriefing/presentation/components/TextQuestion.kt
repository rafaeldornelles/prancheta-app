package com.rafael.featurebriefing.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rafael.baseui.components.TextField
import com.rafael.featurebriefing.domain.entity.BriefingFormQuestion

@Composable
fun TextQuestion(
    modifier: Modifier = Modifier.fillMaxWidth(),
    question: BriefingFormQuestion,
    onValueChange: (id: String, value: String) -> Unit
) {
    TextField(
        modifier = modifier,
        value = question.answer.orEmpty(),
        label = question.question.label,
        placeholder = "",
        onValueChange = { onValueChange(question.question.id, it) }
    )
}

