package com.rafael.prancheta

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.primarySurface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.rafael.baseui.scaffold.BottomBar
import com.rafael.baseui.scaffold.BottomNavigationItem
import com.rafael.baseui.scaffold.Scaffold
import com.rafael.core.common.Routes
import com.rafael.baseui.common.UiState
import com.rafael.baseui.components.HelperAlert
import com.rafael.baseui.theme.spacing
import com.rafael.featureauth.R
import com.rafael.featurebriefing.presentation.ui.BriefingScreen
import com.rafael.featurebriefing.presentation.viewmodel.BriefingViewModel
import com.rafael.featureproject.presentation.ui.ProjectScreen
import com.rafael.featureproject.presentation.viewmodel.ProjectViewModel
import org.koin.androidx.compose.getViewModel

fun NavGraphBuilder.homeGraph(
    navController: NavController,
    briefingViewModel: BriefingViewModel,
    projectViewModel: ProjectViewModel
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