package com.rafael.featureauth.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.rafael.core.common.Routes
import com.rafael.featureauth.presentation.ui.CreateAccountScreen
import com.rafael.featureauth.presentation.ui.LoginScreen

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(startDestination = AuthRoutes.Login.route, route = GRAPH_ROUTE) {
        composable(AuthRoutes.Login.route) {
            LoginScreen(navController)
        }
        composable(AuthRoutes.CreateAccount.route) {
            CreateAccountScreen()
        }
    }
}

const val GRAPH_ROUTE = "auth"

sealed class AuthRoutes (route: String) : Routes(route) {
    object Login: AuthRoutes("login")
    object CreateAccount: AuthRoutes("create-account")
}