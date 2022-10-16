package com.rafael.featurebriefing.presentation.navigation

import androidx.compose.material.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.rafael.core.common.Routes
import com.rafael.featurebriefing.presentation.ui.AnswerBriefingScren
import com.rafael.featurebriefing.presentation.ui.SendBriefingScreen

fun NavGraphBuilder.briefingGraph(
    navController: NavController
) {
    navigation(startDestination = BriefingRoutes.SendBriefing.route, route = BriefingRoutes.NAV_ROUTE) {
        composable(BriefingRoutes.SendBriefing.route) {
            SendBriefingScreen()
        }
        composable("${BriefingRoutes.AnswerBriefing.route}?$BRIEFING_ID_ARG={$BRIEFING_ID_ARG}",
            deepLinks = listOf(navDeepLink { uriPattern = DEEPLINK_PATTERN }),
            arguments = listOf(navArgument(BRIEFING_ID_ARG) { type = NavType.StringType })
        ) { entry ->
            AnswerBriefingScren(formId = entry.arguments?.getString(BRIEFING_ID_ARG).orEmpty())
        }
    }
}

private const val BRIEFING_ID_ARG = "id"
private const val DEEPLINK_PATTERN =
    "https://prancheta.com/briefing/answer-briefing?$BRIEFING_ID_ARG={$BRIEFING_ID_ARG}"

sealed class BriefingRoutes(route: String) : Routes(route) {
    object SendBriefing : BriefingRoutes("send-briefing")
    object AnswerBriefing : BriefingRoutes("answer-briefing")

    companion object {
        const val NAV_ROUTE = "briefing"
    }
}