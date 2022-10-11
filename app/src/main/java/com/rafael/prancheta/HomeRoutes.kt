package com.rafael.prancheta

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.rafael.baseui.scaffold.BottomBar
import com.rafael.baseui.scaffold.BottomNavigationItem
import com.rafael.baseui.scaffold.Scaffold
import com.rafael.core.common.Routes
import com.rafael.core.common.UiState
import com.rafael.featureauth.R

fun NavGraphBuilder.homeGraph(navController: NavController) {
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
                Text(text = "Projects")
            }
        }
        composable(HomeRoutes.Briefing.route) {
            Scaffold(
                UiState.Success<Any>(Unit),
                bottomBar = { BottomBar(navController = navController, items = navigationitems) }
            ) {
                Text(text = "Briefing")
            }
        }
    }
}

sealed class HomeRoutes(route: String, @StringRes val label: Int, val icon: ImageVector) :
    Routes(route) {
    object Projects : HomeRoutes("projects", R.string.login_label, Icons.Outlined.List)
    object Briefing :
        HomeRoutes("briefing", R.string.login_label, Icons.Outlined.Search)

    fun toBottomItem() = BottomNavigationItem(route, icon, label)

    companion object {
        const val GRAPH_ROUTE = "home"
    }
}