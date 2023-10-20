package com.rafael.prancheta

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.rafael.baseui.common.UiState
import com.rafael.baseui.scaffold.BottomBar
import com.rafael.baseui.scaffold.BottomNavigationItem
import com.rafael.baseui.scaffold.Scaffold
import com.rafael.core.common.Routes
import com.rafael.featureauth.R
import com.rafael.featurebriefing.presentation.ui.BriefingScreen
import com.rafael.featurebriefing.presentation.viewmodel.BriefingViewModel
import com.rafael.featureproject.presentation.ui.ProjectScreen
import com.rafael.featureproject.presentation.viewmodel.ProjectViewModel

fun NavGraphBuilder.homeGraph(
    navController: NavController,
    briefingViewModel: BriefingViewModel,
    projectViewModel: ProjectViewModel,
) {
    val navigationitems = listOf(
        HomeRoutes.Projects.toBottomItem(),
        HomeRoutes.Briefing.toBottomItem()
    )
    navigation(startDestination = HomeRoutes.Projects.route, route = HomeRoutes.GRAPH_ROUTE) {
        composable(HomeRoutes.Projects.route) {
            Scaffold(
                UiState.Success<Any>(Unit),
                bottomBar = { BottomBar(navController = navController, items = navigationitems) }
            ) {
                ProjectScreen(navController = navController, viewModel = projectViewModel)
            }
        }
        composable(HomeRoutes.Briefing.route) {
            Scaffold(
                UiState.Success<Any>(Unit),
                bottomBar = { BottomBar(navController = navController, items = navigationitems) },
                topBar = {}
            ) {
                BriefingScreen(navController = navController, viewModel = briefingViewModel, onProjectsReload = {
                    projectViewModel.refresh()
                })
            }
        }
    }
}

sealed class HomeRoutes(route: String, @StringRes val label: Int, val icon: ImageVector) :
    Routes(route) {
    object Projects : HomeRoutes("projects", R.string.projects_label, Icons.Outlined.List)
    object Briefing :
        HomeRoutes("briefing-tab", R.string.briefing_label, Icons.Outlined.Search)

    fun toBottomItem() = BottomNavigationItem(route, icon, label)

    companion object {
        const val GRAPH_ROUTE = "home"
    }
}