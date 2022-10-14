package com.rafael.featurebriefing.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.rafael.core.common.Routes
import com.rafael.featurebriefing.presentation.ui.SendBriefingScreen

fun NavGraphBuilder.briefingGraph(
    navController: NavController
) {
    composable(BriefingRoutes.SendBriefing.route) {
        SendBriefingScreen()
    }
}

sealed class BriefingRoutes(route: String) : Routes(route) {
    object SendBriefing : BriefingRoutes("send-briefing")
}