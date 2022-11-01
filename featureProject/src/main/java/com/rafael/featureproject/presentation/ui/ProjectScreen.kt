package com.rafael.featureproject.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rafael.baseui.components.Card
import com.rafael.baseui.components.HelperAlert
import com.rafael.baseui.components.KeyValueText
import com.rafael.baseui.scaffold.Scaffold
import com.rafael.baseui.theme.spacing
import com.rafael.core.extensions.toDaysAgo
import com.rafael.featureproject.presentation.viewmodel.ProjectViewModel
import java.util.*
import org.koin.androidx.compose.getViewModel

@Composable
fun ProjectScreen(
    navController: NavController,
    viewModel: ProjectViewModel
) {
    Scaffold(state = viewModel.uiState, bottomBar = {}, topBar = {}, horizontalPadding = 0.dp) {
        Column {
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.x300))
            Text(text = "Projetos", style = MaterialTheme.typography.h4)
            if (it.projects.isEmpty()) {
                HelperAlert(
                    text = "Você ainda não adicionou nenhum projeto. Faça um briefing antes de adicionar um projeto",
                    vector = Icons.Outlined.Info
                )
            } else {
                Text(text = "Os seus projetos em andamento aparecerão aqui")
                it.projects.forEach {
                    Card(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(MaterialTheme.spacing.x300)) {
                            KeyValueText(key = "Cliente", value = it.clientName)
                            KeyValueText(key = "E-mail", value = it.clientEmail)
                            KeyValueText(key = "Início", value = it.projectStart.toDaysAgo())
                        }
                    }
                }
            }
        }
    }
}