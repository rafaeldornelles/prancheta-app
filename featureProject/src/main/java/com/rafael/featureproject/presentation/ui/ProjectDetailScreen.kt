package com.rafael.featureproject.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.rafael.baseui.components.ChevronRow
import com.rafael.baseui.components.KeyValueRow
import com.rafael.baseui.scaffold.Scaffold
import com.rafael.baseui.theme.spacing
import com.rafael.core.common.withArgs
import com.rafael.featureproject.presentation.navigation.ID_KEY
import com.rafael.featureproject.presentation.navigation.ProjectRoutes
import com.rafael.featureproject.presentation.viewmodel.ProjectDetailViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ProjectDetailScreen(
    navController: NavController,
    projectId: String,
    viewModel: ProjectDetailViewModel = getViewModel() {
        parametersOf(projectId)
    }
) {
    Scaffold(state = viewModel.uiState) { state ->
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.x300))
            Text(text = "Detalhes do projeto", style = MaterialTheme.typography.h4)
            Text(text = "Confira aqui os detalhes do seu projeto")
            KeyValueRow(key = "Cliente", value = state.project.clientName)
            KeyValueRow(key = "E-mail", value = state.project.clientEmail)
            ChevronRow(
                onclick = {
                    navController.navigate("briefing-results?id=${state.project.briefing.id}")
                }
            ) {
                Text(text = "Briefing", style = MaterialTheme.typography.h5)
                Text(text = "Veja aqui as respostas do cliente para o briefing do projeto")
            }
            Divider()
            ChevronRow(
                onclick = {
                    navController.navigate(ProjectRoutes.Construction.withArgs(ID_KEY to state.project.id.orEmpty()))
                }
            ) {
                Text(text = "Acompanhamento da Obra", style = MaterialTheme.typography.h5)
                Text(text = "Verifique ou insira novas visitas à obra")
            }
            Divider()
            ChevronRow() {
                Text(text = "Feedback", style = MaterialTheme.typography.h5)
                Text(text = "Depois que o projeto for concluído, peça para o cliente deixar um feedback!")
            }
        }
    }
}