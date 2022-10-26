package com.rafael.featurebriefing.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.rafael.baseui.common.CurrencyVisualTransformation
import com.rafael.baseui.components.TextField
import com.rafael.featurebriefing.domain.entity.BriefingFormQuestion
import java.text.NumberFormat
import java.util.*

@Composable
fun CurrencyQuestion(
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
        visualTransformation = CurrencyVisualTransformation(),
        leadingIcon = {
            Text(text = NumberFormat.getCurrencyInstance(Locale.getDefault()).currency.symbol)
        }
    )
}