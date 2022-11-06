package com.rafael.featureproject.presentation.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rafael.baseui.components.Button
import com.rafael.baseui.components.TextField
import com.rafael.baseui.scaffold.Scaffold
import com.rafael.baseui.theme.spacing
import com.rafael.featureproject.presentation.navigation.ProjectRoutes
import com.rafael.featureproject.presentation.viewmodel.AnswerFeedbackAction
import com.rafael.featureproject.presentation.viewmodel.AnswerFeedbackViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AnswerFeedbackScreen(
    navController: NavController,
    projectId: String,
    viewModel: AnswerFeedbackViewModel = getViewModel() {
        parametersOf(projectId)
    },
    context: Context = LocalContext.current
) {
    LaunchedEffect(key1 = Unit){
        viewModel.events.collectLatest {
            when(it) {
                AnswerFeedbackAction.Success -> {
                    navController.navigate(ProjectRoutes.AnswerFeedbackSent.route) {
                        popUpTo(0)
                    }
                }
                is AnswerFeedbackAction.Error -> {
                    Toast.makeText(context, it.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    Scaffold(state = viewModel.uiState) { state ->
        Column(Modifier.verticalScroll(rememberScrollState())) {
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.x300))
            Text(text = "Feedback", style = MaterialTheme.typography.h4)
            Text(text = "Por favor, preencha o campo abaixo para enviar ao seu arquiteto um feedback relativo aos serviços prestados.")
            TextField(state = state.inputState, onValueChange = viewModel::onInputStateChange, lineCount = Int.MAX_VALUE, modifier = Modifier
                .fillMaxWidth()
                .height(200.dp))
            Button(modifier = Modifier.fillMaxWidth(), text = "Enviar", state = state.buttonState) {
                viewModel.onSendFeedback()
            }
        }

    }
}