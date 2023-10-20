package com.rafael.featurebriefing.presentation.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.rafael.baseui.components.Button
import com.rafael.baseui.scaffold.Scaffold
import com.rafael.baseui.theme.spacing
import com.rafael.core.model.QuestionType
import com.rafael.featurebriefing.presentation.components.CheckboxQuestion
import com.rafael.featurebriefing.presentation.components.CurrencyQuestion
import com.rafael.featurebriefing.presentation.components.NumberQuestion
import com.rafael.featurebriefing.presentation.components.RadioImageQuestion
import com.rafael.featurebriefing.presentation.components.RadioQuestion
import com.rafael.featurebriefing.presentation.components.TextQuestion
import com.rafael.featurebriefing.presentation.navigation.BriefingRoutes
import com.rafael.featurebriefing.presentation.viewmodel.AnswerBriefingAction
import com.rafael.featurebriefing.presentation.viewmodel.AnswerBriefingViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AnswerBriefingScren(
    navController: NavController,
    formId: String,
    context: Context = LocalContext.current,
    viewModel: AnswerBriefingViewModel = getViewModel() {
        parametersOf(formId)
    }
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.action.collectLatest {
            when (it) {
                AnswerBriefingAction.Sent -> navController.navigate(BriefingRoutes.BriefingSent.route) {
                    popUpTo(
                        0
                    )
                }
                is AnswerBriefingAction.Error -> Toast.makeText(
                    context,
                    "Não foi possível enviar o briefing",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    Scaffold(state = viewModel.uiState) {
        Column(Modifier.verticalScroll(rememberScrollState())) {
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.x300))

            Text(
                "Olá, ${it.form.clientName.split(" ").first()}",
                style = MaterialTheme.typography.h4
            )
            Text(text = "Por favor. responda a estas perguntas para que possamos ter idéia do que deseja fazer com seu projeto")
            Divider(Modifier.padding(vertical = MaterialTheme.spacing.x300))
            it.form.questions.forEach { q ->
                when (q.question.questionType) {
                    QuestionType.TEXT -> TextQuestion(
                        question = q,
                        onValueChange = viewModel::onQuestionAnswered
                    )
                    QuestionType.NUMBER -> NumberQuestion(
                        question = q,
                        onValueChange = viewModel::onQuestionAnswered
                    )
                    QuestionType.CHECKBOX -> CheckboxQuestion(
                        question = q,
                        onValueChange = viewModel::onQuestionAnswered
                    )
                    QuestionType.CURRENCY -> CurrencyQuestion(
                        question = q,
                        onValueChange = viewModel::onQuestionAnswered
                    )

                    QuestionType.RADIO -> RadioQuestion(
                        question = q,
                        onValueChange = viewModel::onQuestionAnswered
                    )

                    QuestionType.RADIOIMAGE -> RadioImageQuestion(
                        question = q,
                        onValueChange = viewModel::onQuestionAnswered
                    )

                    QuestionType.YESNO -> RadioQuestion(
                        question = q.copy(
                            question = q.question.copy(
                                options = listOf(
                                    "Sim",
                                    "Não"
                                )
                            )
                        ),
                        onValueChange = viewModel::onQuestionAnswered
                    )
                }
                Divider(Modifier.padding(vertical = MaterialTheme.spacing.x300))
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.spacing.x300),
                text = "Enviar",
                state = it.buttonState
            ) {
                viewModel.onSendForm()
            }
        }
    }
}

