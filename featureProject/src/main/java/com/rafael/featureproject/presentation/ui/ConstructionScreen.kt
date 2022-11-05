package com.rafael.featureproject.presentation.ui

import OnLifecycleEvent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.rafael.baseui.components.ChevronRow
import com.rafael.baseui.scaffold.Scaffold
import com.rafael.baseui.theme.spacing
import com.rafael.core.common.withArgs
import com.rafael.core.extensions.formatDate
import com.rafael.featureproject.presentation.navigation.ID_KEY
import com.rafael.featureproject.presentation.navigation.ID_SECONDARY_KEY
import com.rafael.featureproject.presentation.navigation.ProjectRoutes
import com.rafael.featureproject.presentation.viewmodel.ConstructionViewModel
import java.util.*
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ConstructionScreen(
    navController: NavController,
    projectId: String,
    viewModel: ConstructionViewModel = getViewModel() {
        parametersOf(projectId)
    }
) {
    OnLifecycleEvent { owner, event ->
        if (viewModel.uiState.getOrNull() != null && event == Lifecycle.Event.ON_RESUME) {
            viewModel.refresh()
        }
    }
    Scaffold(state = viewModel.uiState) { state ->
        Column(Modifier.verticalScroll(rememberScrollState())) {
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.x300))
            Text(text = "Acompanhamento da obra", style = MaterialTheme.typography.h4)
            Text(text = "Atualize o status da obra com o passar do tempo")
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.x300))
            Row(verticalAlignment = Alignment.Bottom) {
                Column(Modifier.weight(1f)) {
                    Text(text = "Visitas", style = MaterialTheme.typography.h5)
                    Text(text = "Verifique ou adicione visitas à obra")
                }
                IconButton(onClick = {
                    navController.navigate(ProjectRoutes.AddVisitation.withArgs(ID_KEY to projectId))
                }) {
                    Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
                }
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.x200))
            if (state.visitations.isEmpty()) {
                Text(
                    modifier = Modifier.padding(MaterialTheme.spacing.x300),
                    text = "Você ainda não adicionou nenhuma visita à obra. Clique no botao para adicionar sua primeira visita",
                    textAlign = TextAlign.Center
                )
                Divider()
            } else {
                state.visitations.forEach {
                    ChevronRow(
                        onclick = {
                            navController.navigate(ProjectRoutes.VisitationDetail.withArgs(
                                ID_KEY to it.id.orEmpty(), ID_SECONDARY_KEY to projectId
                            ))
                        }
                    ) {
                        Text(text = it.date.formatDate())
                    }
                }
            }
        }
    }
}