package com.rafael.featurebriefing.presentation.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.rafael.baseui.scaffold.Scaffold
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
        it.form.questions.forEach {
            Text(text = it.question.label)
        }
    }
}