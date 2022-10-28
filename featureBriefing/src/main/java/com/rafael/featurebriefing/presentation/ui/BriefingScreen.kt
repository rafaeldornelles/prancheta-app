package com.rafael.featurebriefing.presentation.ui

import OnLifecycleEvent
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.rafael.baseui.components.Button
import com.rafael.baseui.components.Card
import com.rafael.baseui.components.HelperAlert
import com.rafael.baseui.components.KeyValueText
import com.rafael.baseui.scaffold.Scaffold
import com.rafael.baseui.scaffold.TopAppBar
import com.rafael.baseui.scaffold.TopAppBarAction
import com.rafael.baseui.theme.spacing
import com.rafael.core.common.withArgs
import com.rafael.featurebriefing.presentation.navigation.BriefingRoutes
import com.rafael.featurebriefing.presentation.navigation.BriefingRoutes.Companion.BRIEFING_ID_KEY
import com.rafael.featurebriefing.presentation.viewmodel.BriefingViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun BriefingScreen(
    navController: NavController,
    viewModel: BriefingViewModel = getViewModel()
) {
    OnLifecycleEvent{ owner, event ->
        if (event == Lifecycle.Event.ON_RESUME) {
            viewModel.refresh()
        }
    }
    Scaffold(
        state = viewModel.uiState,
        topBar = {
            TopAppBar(
                scaffoldState = it,
                actions = listOf(
                    TopAppBarAction(
                        "Adicionar",
                        Icons.Outlined.Add
                    ) { navController.navigate(BriefingRoutes.SendBriefing.route) }
                )
            )
        },
        horizontalPadding = 0.dp
    ) { state ->
        if (state.briefings.isEmpty()) {
            Column {
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.x300))
                Text("Briefing", style = MaterialTheme.typography.h4)
                HelperAlert(
                    text = "Você ainda não adiciou nenhum briefing. Envie um briefing pra um cliente e ele deverá aparecer aqui",
                    vector = Icons.Outlined.Info
                )
            }
        } else {
            LazyColumn {
                item {
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.x300))
                    Text(text = "Briefing", style = MaterialTheme.typography.h4)
                }
                item {
                    Text(text = "Os briefings que foram enviados aos seus clientes aparecerão aqui")
                }
                items(state.briefings) {
                    val isAnswered = it.answerTime != null
                    Card(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(MaterialTheme.spacing.x300)) {
                            KeyValueText(key = "Cliente", value = it.clientName)
                            Spacer(Modifier.height(MaterialTheme.spacing.x100))
                            KeyValueText(key = "E-mail", value = it.clientEmail)
                            Spacer(Modifier.height(MaterialTheme.spacing.x100))
                            KeyValueText(
                                key = "Status",
                                value = if (isAnswered) "Não respondido" else "Respondido"
                            )
                            if (isAnswered) {
                                Row {
                                   Button(text = "Ver Respostas") {
                                       navController.navigate(BriefingRoutes.BriefingResults.withArgs(BRIEFING_ID_KEY to it.id.orEmpty()))
                                   }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}