package com.rafael.prancheta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.rafael.baseui.theme.PranchetaTheme
import com.rafael.core.cache.TokenCache
import com.rafael.featureauth.presentation.navigation.AuthRoutes
import com.rafael.featureauth.presentation.navigation.authGraph
import com.rafael.featurebriefing.presentation.navigation.briefingGraph
import com.rafael.featurebriefing.presentation.viewmodel.BriefingViewModel
import com.rafael.featureproject.presentation.navigation.projectGraph
import com.rafael.featureproject.presentation.viewmodel.ProjectViewModel
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val tokenCache: TokenCache by inject()
    private val briefingViewModel: BriefingViewModel by inject()
    private val projectViewModel: ProjectViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Prancheta)
        setContent {
            PranchetaTheme {
                val navController = rememberNavController()
                LaunchedEffect(tokenCache.authState.value) {
                    if (tokenCache.authState.value) {
                        projectViewModel.refresh()
                        briefingViewModel.refresh()
                        navController.navigate(HomeRoutes.GRAPH_ROUTE) {
                            popUpTo(0)
                        }
                    } else {
                        navController.navigate(AuthRoutes.GRAPH_ROUTE) {
                            popUpTo(0)
                        }
                    }
                }
                NavHost(
                    navController = navController,
                    startDestination = if (tokenCache.token == null) AuthRoutes.GRAPH_ROUTE else HomeRoutes.GRAPH_ROUTE
                ) {
                    authGraph(navController)
                    homeGraph(navController, briefingViewModel, projectViewModel)
                    briefingGraph(navController, briefingViewModel)
                    projectGraph(navController)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        briefingViewModel.refresh()
        projectViewModel.refresh()
    }
}