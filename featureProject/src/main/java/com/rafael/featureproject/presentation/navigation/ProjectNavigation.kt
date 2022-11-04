package com.rafael.featureproject.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.rafael.core.common.Routes
import com.rafael.featureproject.presentation.ui.AddVisitationScreen
import com.rafael.featureproject.presentation.ui.ConstructionScreen
import com.rafael.featureproject.presentation.ui.ProjectDetailScreen

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
}

const val ID_KEY = "id"

sealed class ProjectRoutes(route: String) : Routes(route) {
    object ProjectDetail : ProjectRoutes("project-detail")
    object Construction : ProjectRoutes("constuction")
    object AddVisitation: ProjectRoutes("add-visitation")
}