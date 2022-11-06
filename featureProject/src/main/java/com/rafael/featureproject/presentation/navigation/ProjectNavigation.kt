package com.rafael.featureproject.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.rafael.core.common.Routes
import com.rafael.featureproject.presentation.ui.AddVisitationScreen
import com.rafael.featureproject.presentation.ui.AnswerFeedbackScreen
import com.rafael.featureproject.presentation.ui.ConstructionScreen
import com.rafael.featureproject.presentation.ui.FeedbackAnsweredScreen
import com.rafael.featureproject.presentation.ui.FeedbackScreen
import com.rafael.featureproject.presentation.ui.ProjectDetailScreen
import com.rafael.featureproject.presentation.ui.VisitationDetailScreen

fun NavGraphBuilder.projectGraph(
    navController: NavController
) {
    composable("${ProjectRoutes.ProjectDetail.route}?$ID_KEY={$ID_KEY}", arguments = listOf(
        navArgument(ID_KEY) {
            type = NavType.StringType
        }
    )) { entry ->
        ProjectDetailScreen(
            navController = navController,
            projectId = entry.arguments?.getString(ID_KEY).orEmpty()
        )
    }
    composable("${ProjectRoutes.Construction.route}?$ID_KEY={$ID_KEY}",
        arguments = listOf(
            navArgument(ID_KEY) {
                type = NavType.StringType
            }
        )
    ) { entry ->
        ConstructionScreen(
            navController = navController,
            projectId = entry.arguments?.getString(ID_KEY).orEmpty()
        )
    }
    composable("${ProjectRoutes.AddVisitation.route}?$ID_KEY={$ID_KEY}",
        arguments = listOf(
            navArgument(ID_KEY) {
                type = NavType.StringType
            }
        )
    ) { entry ->
        AddVisitationScreen(
            navController = navController,
            projectId = entry.arguments?.getString(ID_KEY).orEmpty()
        )
    }
    composable(
        route = "${ProjectRoutes.VisitationDetail.route}?$ID_KEY={$ID_KEY}&$ID_SECONDARY_KEY={$ID_SECONDARY_KEY}",
        arguments = listOf(
            navArgument(ID_KEY) {
                type = NavType.StringType
            },
            navArgument(ID_SECONDARY_KEY) {
                type = NavType.StringType
            }
        )
    ) { entry ->
        VisitationDetailScreen(
            navController = navController,
            visitationId = entry.arguments?.getString(ID_KEY).orEmpty(),
            projectId = entry.arguments?.getString(ID_SECONDARY_KEY).orEmpty()
        )
    }
    composable("${ProjectRoutes.Feedback.route}?$ID_KEY={$ID_KEY}",
        arguments = listOf(
            navArgument(ID_KEY) {
                type = NavType.StringType
            }
        )
    ) { entry ->
        FeedbackScreen(
            navController = navController,
            projectId = entry.arguments?.getString(ID_KEY).orEmpty()
        )
    }
    composable(
        "${ProjectRoutes.AnswerFeedback.route}?$ID_KEY={$ID_KEY}",
        arguments = listOf(
            navArgument(ID_KEY) {
                type = NavType.StringType
            },
        ),
        deepLinks = listOf(navDeepLink { uriPattern = DEEPLINK_PATTERN }),
    ) { entry ->
        AnswerFeedbackScreen(
            navController = navController,
            projectId = entry.arguments?.getString(ID_KEY).orEmpty()
        )
    }

    composable(ProjectRoutes.AnswerFeedbackSent.route) {
        FeedbackAnsweredScreen()
    }
}

const val ID_KEY = "id"
const val ID_SECONDARY_KEY = "secondaryId"
private const val DEEPLINK_PATTERN =
    "https://prancheta.com/project/answer-feedback?$ID_KEY={$ID_KEY}"

sealed class ProjectRoutes(route: String) : Routes(route) {
    object ProjectDetail : ProjectRoutes("project-detail")
    object Construction : ProjectRoutes("constuction")
    object AddVisitation : ProjectRoutes("add-visitation")
    object VisitationDetail : ProjectRoutes("visitation-detail")
    object Feedback : ProjectRoutes("feedback")
    object AnswerFeedback : ProjectRoutes("answer-feedback")
    object AnswerFeedbackSent : ProjectRoutes("answer-feedback-sent")
}