package com.rafael.featurebriefing.presentation.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rafael.baseui.components.Button
import com.rafael.baseui.components.Card
import com.rafael.baseui.components.KeyValueText
import com.rafael.baseui.components.SelectionItem
import com.rafael.baseui.scaffold.Scaffold
import com.rafael.baseui.theme.spacing
import com.rafael.featurebriefing.presentation.viewmodel.BriefingViewModel
import com.rafael.featurebriefing.presentation.viewmodel.SendBriefingAction
import com.rafael.featurebriefing.presentation.viewmodel.SendBriefingViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun SendBriefingScreen(
    navController: NavController,
    viewModel: SendBriefingViewModel = getViewModel() {
        parametersOf(selectedBriefing, clientName, clientEmail)
    },
    briefingViewModel: BriefingViewModel,
    context: Context = LocalContext.current,
    clientName: String,
    clientEmail: String,
    selectedBriefing: String
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.action.collectLatest {
            when (it) {
                SendBriefingAction.BriefingSent -> {
                    Toast.makeText(context, "Briefing enviado com sucesso", Toast.LENGTH_SHORT)
                        .show()
                    briefingViewModel.refresh()
                    navController.popBackStack("briefing-tab", false)
                }

                is SendBriefingAction.Error -> {
                    Toast.makeText(
                        context,
                        "Não foi possível enviar o briefing.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
    Scaffold(state = viewModel.uiState) { state ->
        Column(Modifier.verticalScroll(rememberScrollState())) {
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.x300))
            Text(
                text = "Quase lá...",
                modifier = Modifier.padding(bottom = MaterialTheme.spacing.x300),
                style = MaterialTheme.typography.h4
            )
            Text(text = "Confirme os dados e as perguntas a serem enviadas e quanto estiver tudo pronto, clique em enviar.")
            Row {
                Text(text = "Cliente: ", fontWeight = FontWeight.Black)
                Text(text = clientName)
            }
            Row {
                Text(text = "Email: ", fontWeight = FontWeight.Black)
                Text(text = clientEmail)
            }
            Divider(
                modifier = Modifier.padding(vertical = MaterialTheme.spacing.x300),
                color = MaterialTheme.colors.primary,
                thickness = 1.dp
            )

            Text(text = "3. Selecione as perguntas que serão enviadas")
            state.questions.forEach { q ->
                Card {
                    SelectionItem(
                        modifier = Modifier.padding(vertical = MaterialTheme.spacing.x300),
                        isSelected = q.isSelected,
                        alignment = Alignment.Top,
                        onSelectChange = { viewModel.onSelectionChange(q.question.id, it) }
                    ) {
                        Column {
                            Spacer(modifier = Modifier.height(MaterialTheme.spacing.x100))
                            KeyValueText(key = "Pergunta", value = q.question.label)
                            Spacer(modifier = Modifier.height(MaterialTheme.spacing.x100))
                            KeyValueText(
                                key = "Tipo",
                                value = q.question.questionType.questionLabel
                            )
                            Spacer(modifier = Modifier.height(MaterialTheme.spacing.x100))
                            if (q.question.options?.isNotEmpty() == true) {
                                KeyValueText(
                                    key = "Opções",
                                    value = q.question.options?.joinToString(", ").orEmpty()
                                )
                                Spacer(modifier = Modifier.height(MaterialTheme.spacing.x100))
                            }
                        }
                    }
                }
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.spacing.x300),
                state = state.buttonState,
                text = "Enviar",
                onClick = viewModel::sendBriefing
            )
        }

    }
}