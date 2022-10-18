package com.rafael.featurebriefing.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rafael.baseui.components.Button
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
                }
            }
            Button(modifier = Modifier.fillMaxWidth(), text = "Enviar", state = it.buttonState) {
                viewModel.onSendForm()
            }
        }
    }
}