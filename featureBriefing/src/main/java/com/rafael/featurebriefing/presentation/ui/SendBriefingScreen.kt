package com.rafael.featurebriefing.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rafael.baseui.components.Button
import com.rafael.baseui.components.SelectionItem
import com.rafael.baseui.components.TextField
import com.rafael.baseui.scaffold.Scaffold
import com.rafael.featurebriefing.presentation.viewmodel.SendBriefingViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun SendBriefingScreen(
    viewModel: SendBriefingViewModel = getViewModel()
) {
    Scaffold(state = viewModel.uiState) { state ->
        Column(Modifier.verticalScroll(rememberScrollState())) {
            Text(text = "Send Briefing", style = MaterialTheme.typography.h4)
            Text(text = "Dados do cliente")
            TextField(
                state = state.clientNameState,
                onValueChange = {}
            )
            TextField(
                state = state.clientEmailState,
                onValueChange = {})
            Text(text = "Perguntas a serem enviadas")
            state.questions.forEach { q ->
                SelectionItem(
                    isSelected = q.isSelected,
                    onSelectChange = { viewModel.onSelectionChange(q.question.id, it) }
                ) {
                    Text(text = q.question.label)
                }
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                state = state.buttonState,
                text = "Enviar",
                onClick = viewModel::sendBriefing
            )
        }

    }
}