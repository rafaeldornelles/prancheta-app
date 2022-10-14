package com.rafael.featurebriefing.presentation.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.rafael.baseui.scaffold.Scaffold
import com.rafael.baseui.scaffold.TopAppBar
import com.rafael.baseui.scaffold.TopAppBarAction
import com.rafael.baseui.common.UiState
import com.rafael.featurebriefing.presentation.navigation.BriefingRoutes

@Composable
fun BriefingScreen(
    navController: NavController
) {
    Scaffold(
        state = UiState.Success(Unit),
        topBar = {
            TopAppBar(
                stringResource(com.rafael.baseui.R.string.default_password),
                actions = listOf(
                    TopAppBarAction(
                        "Briefing",
                        Icons.Outlined.Add
                    ) { navController.navigate(BriefingRoutes.SendBriefing.route) }
                )
            )
        }
    ) {
        Text("Briefing", style = MaterialTheme.typography.h4)
    }
}