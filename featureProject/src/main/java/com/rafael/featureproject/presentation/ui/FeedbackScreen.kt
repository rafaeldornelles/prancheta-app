package com.rafael.featureproject.presentation.ui

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.rafael.baseui.components.Button
import com.rafael.baseui.components.ButtonSecondary
import com.rafael.baseui.scaffold.Scaffold
import com.rafael.baseui.theme.spacing
import com.rafael.featureproject.presentation.viewmodel.FeedbackViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun FeedbackScreen(
    navController: NavController,
    projectId: String,
    viewModel: FeedbackViewModel = getViewModel() {
        parametersOf(projectId)
    },
    clipboardManager: ClipboardManager = LocalClipboardManager.current,
    context: Context = LocalContext.current
) {
    Scaffold(state = viewModel.uiState) { state ->
        Column {
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.x300))
            Text(text = "Feedback", style = MaterialTheme.typography.h4)
            when{
                !state.project.isConcluded -> {
                    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "O seu projeto ainda não foi finalizado. Deseja finalizá-lo e solicitar o feedback?", textAlign = TextAlign.Center)
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.x400))
                        Button(text = "Finalizar Projeto e Solicitar Feedback") {
                            viewModel.concludeProject()
                        }
                    }
                }
                state.project.feedback == null -> {
                    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "O seu projeto está aguardando o feedback do cliente. Assim que ele for enviado, deverá aparecer aqui.",
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.x400))
                        Button(text = "Voltar") {
                            navController.popBackStack()
                        }
                        val url =
                            "https://prancheta.com/project/answer-feedback?id=${state.project.id}"
                        ButtonSecondary(text = "Copiar Link") {
                            clipboardManager.setText(AnnotatedString(url))
                            Toast.makeText(
                                context,
                                "Url copiada para área de transferência",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        ButtonSecondary(text = "Responder") {
                            Intent(Intent.ACTION_VIEW, url.toUri()).apply {
                                context.startActivity(
                                    this
                                )
                            }
                            navController.popBackStack()
                        }
                    }
                }
                else -> {
                    Text(text = "O seu projeto recebeu um feedback: Confira abaixo o comentário do cliente.")
                    Text(text = "Resposta do Cliente", style = MaterialTheme.typography.h5)
                    Text(text = state.project.feedback.orEmpty())

                }
            }
        }

    }
}