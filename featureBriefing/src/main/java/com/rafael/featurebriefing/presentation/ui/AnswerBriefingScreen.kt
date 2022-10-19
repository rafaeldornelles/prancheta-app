package com.rafael.featurebriefing.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.rafael.baseui.common.CurrencyVisualTransformation
import com.rafael.baseui.components.Button
import com.rafael.baseui.components.SelectionItem
import com.rafael.baseui.components.TextField
import com.rafael.baseui.components.TextFieldState
import com.rafael.baseui.scaffold.Scaffold
import com.rafael.featurebriefing.domain.entity.QuestionType
import com.rafael.featurebriefing.presentation.viewmodel.AnswerBriefingViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AnswerBriefingScren(
    formId: String,
    viewModel: AnswerBriefingViewModel = getViewModel() {
        parametersOf(formId)
    }
) {
    Scaffold(state = viewModel.uiState) {
        Column {
            Text("Olá, ${it.form.clientName}")
            Text(text = "Por favor. responda a estas perguntas para que possamos ter idéia do que deseja fazer com seu projeto")
            it.form.questions.forEach { q ->
                when(q.question.questionType) {
                    QuestionType.TEXT -> {
                        TextField(
                            value = q.answer.orEmpty(),
                            label = q.question.label,
                            placeholder = "",
                            onValueChange = { viewModel.onQuestionAnswered(q.question.id, it) }
                        )
                    }
                    QuestionType.NUMBER -> {
                        TextField(
                            value = q.answer.orEmpty(),
                            label = q.question.label,
                            placeholder = q.question.placeholder,
                            onValueChange = { viewModel.onQuestionAnswered(q.question.id, it.filter { it.isDigit() })},
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            trailingIcon = q.question.trailingText?.let {
                                {
                                    Text(text = it)
                                }
                            }
                        )
                    }
                    QuestionType.CHECKBOX -> {
                        Text(text = q.question.label)
                        val itemsSelected = q.answer?.split("|")?.toMutableSet() ?: mutableSetOf()
                        q.question.options?.forEach { option ->
                            SelectionItem(isSelected = option in itemsSelected, onSelectChange = {
                                if (!itemsSelected.add(option)) itemsSelected.remove(option)
                                viewModel.onQuestionAnswered(q.question.id, itemsSelected.joinToString("|"))
                            }) {
                                Text(option)
                            }
                        }
                    }
                    QuestionType.CURRENCY -> {
                        TextField(
                            value = q.answer.orEmpty(),
                            label = q.question.label,
                            placeholder = q.question.placeholder,
                            onValueChange = { viewModel.onQuestionAnswered(q.question.id, it.filter { it.isDigit() })},
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            visualTransformation = CurrencyVisualTransformation()
                        )
                    }
                    else -> {}
                }
            }
            Button(modifier = Modifier.fillMaxWidth(), text = "Enviar", state = it.buttonState) {
                viewModel.onSendForm()
            }
        }
    }
}