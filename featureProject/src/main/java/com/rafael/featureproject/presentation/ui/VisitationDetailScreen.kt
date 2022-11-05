package com.rafael.featureproject.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rafael.baseui.scaffold.Scaffold
import com.rafael.baseui.theme.spacing
import com.rafael.core.extensions.formatDate
import com.rafael.core.extensions.toBitmap
import com.rafael.featureproject.presentation.viewmodel.VisitationDetailViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun VisitationDetailScreen(
    navController: NavController,
    projectId: String,
    visitationId: String,
    viewModel: VisitationDetailViewModel = getViewModel() {
        parametersOf(projectId, visitationId)
    }
) {
    Scaffold(state = viewModel.uiState) { state ->
        LazyColumn {
            item {
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.x300))
                Text("Detalhes da Visita", style = MaterialTheme.typography.h4)
                Text(text = "Visita realizada em ${state.visitation.date.formatDate()}")
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.x300))
                Divider()
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.x300))
                Text(text = "Observações", style = MaterialTheme.typography.h5)
                Text(text = state.visitation.observation)
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.x300))
                Divider()
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.x300))
                Text(text = "Fotos", style = MaterialTheme.typography.h5)
                LazyRow {
                    items(items = state.visitation.images) {
                        Image(
                            modifier = Modifier
                                .padding(end = MaterialTheme.spacing.x200)
                                .size(150.dp)
                                .aspectRatio(1f),
                            contentScale = ContentScale.Crop,
                            bitmap = it.toBitmap(), contentDescription = null
                        )
                    }
                }
            }
        }
    }
}