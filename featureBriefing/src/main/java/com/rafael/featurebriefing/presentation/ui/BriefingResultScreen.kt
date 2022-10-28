package com.rafael.featurebriefing.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.rafael.baseui.components.KeyValueRow
import com.rafael.baseui.components.KeyValueText
import com.rafael.baseui.scaffold.Scaffold
import com.rafael.baseui.theme.spacing
import com.rafael.featurebriefing.presentation.viewmodel.BriefingResultViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun BriefingResultScreen(
    briefingId: String,
    viewModel: BriefingResultViewModel = getViewModel() {
        parametersOf(briefingId)
    }
) {
    Scaffold(state = viewModel.uiState) {
        LazyColumn {
            item {
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.x300))
                Text("Briefing", style = MaterialTheme.typography.h4)
                Text(text = "Aqui estão as respostas de ${it.form.clientName} para o formulário")
            }
            item {
                KeyValueRow(key = "Cliente", value = it.form.clientName)
                KeyValueRow(key = "E-mail", value = it.form.clientEmail)
            }

            items(it.form.questions) {
                Column(Modifier.padding(top = MaterialTheme.spacing.x300)) {
                    Column(modifier = Modifier.padding(horizontal = MaterialTheme.spacing.x300)) {
                        Text(text = "Pergunta: ${it.question.label}", fontWeight = FontWeight.Medium)
                        KeyValueText(key = "Tipo", value = it.question.questionType.questionLabel, modifier = Modifier.padding(top = MaterialTheme.spacing.x200))
                        if (it.question.options != null) {
                            KeyValueText(key = "Opções", value = it.question.options.joinToString(", "), modifier = Modifier.padding(top = MaterialTheme.spacing.x200))
                        }
                        KeyValueText(key = "Resposta", value = it.answer.orEmpty(), modifier = Modifier.padding(top = MaterialTheme.spacing.x200))
                        if (it.question.optionsUrl != null) {
                            it.question.options?.indexOf(it.answer)?.let { i ->
                                AsyncImage(
                                    modifier = Modifier.height(100.dp).padding(vertical = MaterialTheme.spacing.x200),
                                    model = it.question.optionsUrl[i],
                                    contentDescription = null
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.x300))
                    Divider()
                }
            }
        }
    }
}