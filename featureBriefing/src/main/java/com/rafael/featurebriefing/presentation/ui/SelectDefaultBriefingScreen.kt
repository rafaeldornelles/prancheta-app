package com.rafael.featurebriefing.presentation.ui

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rafael.baseui.components.Button
import com.rafael.baseui.components.RadioItem
import com.rafael.baseui.scaffold.Scaffold
import com.rafael.baseui.theme.spacing
import com.rafael.core.common.withArgs
import com.rafael.featurebriefing.presentation.navigation.BriefingRoutes
import com.rafael.featurebriefing.presentation.navigation.BriefingRoutes.Companion.CLIENT_EMAIL_KEY
import com.rafael.featurebriefing.presentation.navigation.BriefingRoutes.Companion.CLIENT_NAME_KEY
import com.rafael.featurebriefing.presentation.navigation.BriefingRoutes.Companion.DEFAULT_BRIEFING_SELECTED_KEY
import com.rafael.featurebriefing.presentation.viewmodel.SelectDefaultBriefingViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun SelectDefaultBriefingScreen(
    navController: NavController,
    viewmodel: SelectDefaultBriefingViewModel = getViewModel(),
    clientName: String,
    clientEmail: String
) {
    Scaffold(state = viewmodel.uiState) { state ->
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.x300))
            Text(
                text = "Selecionar Questionário",
                modifier = Modifier.padding(bottom = MaterialTheme.spacing.x300),
                style = MaterialTheme.typography.h4
            )
            Text(text = "Preparamos questionários pré definidos conforme o tipo do projeto. Selecione o tipo desejado.")
            Divider(
                modifier = Modifier.padding(vertical = MaterialTheme.spacing.x300),
                color = MaterialTheme.colors.primary,
                thickness = 1.dp
            )
            Text(text = "2. Selecionar o tipo de projeto")
            state.defaultBriefings.forEach { db ->
                RadioItem(
                    isSelected = state.currentSelection == db.id,
                    value = db.id,
                    onSelectChange = { viewmodel.onSelected(db.id) }
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = MaterialTheme.spacing.x300)
                    ) {
                        Text(text = db.name)
                        db.description?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.body2
                            )
                        }
                    }
                }
            }
            Button(
                state = state.buttonState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.spacing.x300),
                text = "Próximo"
            ) {
                navController.navigate(
                    BriefingRoutes.SendBriefing.withArgs(
                        CLIENT_NAME_KEY to clientName,
                        CLIENT_EMAIL_KEY to clientEmail,
                        DEFAULT_BRIEFING_SELECTED_KEY to state.currentSelection!!
                    )
                )
            }
        }
    }
}