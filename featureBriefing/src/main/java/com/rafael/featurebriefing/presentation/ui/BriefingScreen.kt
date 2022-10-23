package com.rafael.featurebriefing.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.rafael.baseui.scaffold.Scaffold
import com.rafael.baseui.scaffold.TopAppBar
import com.rafael.baseui.scaffold.TopAppBarAction
import com.rafael.baseui.common.UiState
import com.rafael.baseui.components.HelperAlert
import com.rafael.featurebriefing.presentation.navigation.BriefingRoutes
import com.rafael.featurebriefing.presentation.viewmodel.BriefingViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun BriefingScreen(
    navController: NavController,
    viewModel: BriefingViewModel = getViewModel()
) {
    Scaffold(
        state = viewModel.uiState,
        topBar = {
            TopAppBar(
                scaffoldState = it,
                title = stringResource(com.rafael.baseui.R.string.default_password),
                actions = listOf(
                    TopAppBarAction(
                        "Briefing",
                        Icons.Outlined.Add
                    ) { navController.navigate(BriefingRoutes.SendBriefing.route) }
                )
            )
        }
    ) { state ->
        if (state.briefings.isEmpty()) {
            Column {
                Text("Briefing", style = MaterialTheme.typography.h4)
                HelperAlert(
                    text = "Você ainda não adiciou nenhum briefing. Envie um briefing pra um cliente e ele deverá aparecer aqui",
                    vector = Icons.Outlined.Info
                )
            }
        } else {
            LazyColumn {
                items(state.briefings) {
                    Text(text = it.clientName)
                    Divider()
                }
            }
        }
    }
}