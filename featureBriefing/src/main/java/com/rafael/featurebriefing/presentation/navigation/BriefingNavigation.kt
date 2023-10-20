package com.rafael.featurebriefing.presentation.navigation

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
import com.rafael.featurebriefing.presentation.ui.SelectDefaultBriefingScreen
import com.rafael.featurebriefing.presentation.ui.SendBriefingScreen
import com.rafael.featurebriefing.presentation.viewmodel.BriefingViewModel
import com.rafael.featurebriefing.presentation.viewmodel.FillBriefingClientInfoScreen

fun NavGraphBuilder.briefingGraph(
    navController: NavController,
    briefingViewModel: BriefingViewModel
) {
    navigation(
        startDestination = BriefingRoutes.SendBriefing.route,
        route = BriefingRoutes.NAV_ROUTE
    ) {
        composable(
            BriefingRoutes.SendBriefing.route + "?$CLIENT_NAME_ARG={$CLIENT_NAME_ARG}&$CLIENT_EMAIL_ARG={$CLIENT_EMAIL_ARG}&$DEFAULT_BRIEFING_SELECTED_ARG={$DEFAULT_BRIEFING_SELECTED_ARG}",
            arguments = listOf(
                navArgument(CLIENT_NAME_ARG) {
                    type = NavType.StringType
                },
                navArgument(CLIENT_EMAIL_ARG) {
                    type = NavType.StringType
                },
                navArgument(DEFAULT_BRIEFING_SELECTED_ARG) {
                    type = NavType.StringType
                }
            )
        )
        { entry ->
            SendBriefingScreen(
                navController,
                briefingViewModel = briefingViewModel,
                clientName = entry.arguments?.getString(CLIENT_NAME_ARG).orEmpty(),
                clientEmail = entry.arguments?.getString(CLIENT_EMAIL_ARG).orEmpty(),
                selectedBriefing = entry.arguments?.getString(DEFAULT_BRIEFING_SELECTED_ARG)
                    .orEmpty(),
            )
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
        composable(
            BriefingRoutes.SelectDefaultBriefing.route + "?$CLIENT_NAME_ARG={$CLIENT_NAME_ARG}&$CLIENT_EMAIL_ARG={$CLIENT_EMAIL_ARG}",
            arguments = listOf(
                navArgument(CLIENT_NAME_ARG) {
                    type = NavType.StringType
                },
                navArgument(CLIENT_EMAIL_ARG) {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            SelectDefaultBriefingScreen(
                navController,
                clientName = entry.arguments?.getString(CLIENT_NAME_ARG).orEmpty(),
                clientEmail = entry.arguments?.getString(CLIENT_EMAIL_ARG).orEmpty()
            )
        }
        composable(BriefingRoutes.FillBriefingClientInfo.route) {
            FillBriefingClientInfoScreen(navController)
        }
    }
}

private const val BRIEFING_ID_ARG = "id"
private const val CLIENT_NAME_ARG = "clientName"
private const val CLIENT_EMAIL_ARG = "clientEmail"
private const val DEFAULT_BRIEFING_SELECTED_ARG = "defaultBriefing"
private const val DEEPLINK_PATTERN =
    "https://prancheta.com/briefing/answer-briefing?$BRIEFING_ID_ARG={$BRIEFING_ID_ARG}"

sealed class BriefingRoutes(route: String) : Routes(route) {
    object SendBriefing : BriefingRoutes("send-briefing")
    object AnswerBriefing : BriefingRoutes("answer-briefing")
    object BriefingSent : BriefingRoutes("briefing-sent")
    object BriefingResults : BriefingRoutes("briefing-results")
    object SelectDefaultBriefing : BriefingRoutes("select-default-briefing")
    object FillBriefingClientInfo : BriefingRoutes("fill-briefing-client-info")

    companion object {
        const val NAV_ROUTE = "briefing"
        const val BRIEFING_ID_KEY = BRIEFING_ID_ARG
        const val CLIENT_NAME_KEY = CLIENT_NAME_ARG
        const val CLIENT_EMAIL_KEY = CLIENT_EMAIL_ARG
        const val DEFAULT_BRIEFING_SELECTED_KEY = DEFAULT_BRIEFING_SELECTED_ARG
    }
}