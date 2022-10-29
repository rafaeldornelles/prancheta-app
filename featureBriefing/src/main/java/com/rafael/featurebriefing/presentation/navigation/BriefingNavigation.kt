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
import com.rafael.featurebriefing.presentation.ui.BriefingResultScreen
import com.rafael.featurebriefing.presentation.ui.BriefingSentScreen
import com.rafael.featurebriefing.presentation.ui.SendBriefingScreen
import com.rafael.featurebriefing.presentation.viewmodel.BriefingViewModel

fun NavGraphBuilder.briefingGraph(
    navController: NavController,
    briefingViewModel: BriefingViewModel
) {
    navigation(
        startDestination = BriefingRoutes.SendBriefing.route,
        route = BriefingRoutes.NAV_ROUTE
    ) {
        composable(BriefingRoutes.SendBriefing.route) {
            SendBriefingScreen(navController, briefingViewModel =  briefingViewModel)
        }
        composable(
            "${BriefingRoutes.AnswerBriefing.route}?$BRIEFING_ID_ARG={$BRIEFING_ID_ARG}",
            deepLinks = listOf(navDeepLink { uriPattern = DEEPLINK_PATTERN }),
            arguments = listOf(navArgument(BRIEFING_ID_ARG) { type = NavType.StringType })
        ) { entry ->
            AnswerBriefingScren(
                navController = navController,
                formId = entry.arguments?.getString(BRIEFING_ID_ARG).orEmpty()
            )
        }
        composable(BriefingRoutes.BriefingSent.route) {
            BriefingSentScreen()
        }
        composable(
            BriefingRoutes.BriefingResults.route + "?$BRIEFING_ID_ARG={$BRIEFING_ID_ARG}",
            arguments = listOf(
                navArgument(BRIEFING_ID_ARG) {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            BriefingResultScreen(briefingId = entry.arguments?.getString(BRIEFING_ID_ARG).orEmpty())
        }
    }
}

private const val BRIEFING_ID_ARG = "id"
private const val DEEPLINK_PATTERN =
    "https://prancheta.com/briefing/answer-briefing?$BRIEFING_ID_ARG={$BRIEFING_ID_ARG}"

sealed class BriefingRoutes(route: String) : Routes(route) {
    object SendBriefing : BriefingRoutes("send-briefing")
    object AnswerBriefing : BriefingRoutes("answer-briefing")
    object BriefingSent : BriefingRoutes("briefing-sent")
    object BriefingResults : BriefingRoutes("briefing-results")

    companion object {
        const val NAV_ROUTE = "briefing"
        const val BRIEFING_ID_KEY = "id"
    }
}