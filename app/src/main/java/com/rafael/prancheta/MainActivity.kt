package com.rafael.prancheta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.rafael.baseui.theme.PranchetaTheme
import com.rafael.core.cache.UserCache
import com.rafael.featureauth.presentation.navigation.AuthRoutes
import com.rafael.featureauth.presentation.navigation.authGraph
import com.rafael.featurebriefing.presentation.navigation.briefingGraph
import com.rafael.featurebriefing.presentation.viewmodel.BriefingViewModel
import com.rafael.featureproject.presentation.navigation.projectGraph
import com.rafael.featureproject.presentation.viewmodel.ProjectViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.getViewModel

class MainActivity : ComponentActivity() {

    private val auth = FirebaseAuth.getInstance()
    private val userCache: UserCache by inject()
    private lateinit var briefingViewModel: BriefingViewModel
    private lateinit var projectViewModel: ProjectViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Prancheta)
        setContent {
            PranchetaTheme {
                val navController = rememberNavController()
                briefingViewModel = getViewModel()
                projectViewModel = getViewModel()
                auth.addAuthStateListener {
                    userCache.currentUserId = it.currentUser?.uid
                    briefingViewModel.refresh()
                    projectViewModel.refresh()
                    if (it.currentUser == null) {
                        navController.navigate(AuthRoutes.Login.route) {
                            popUpTo(0)
                        }
                    } else {
                        navController.navigate(HomeRoutes.GRAPH_ROUTE) {
                            popUpTo(0)
                        }
                    }
                }
                NavHost(
                    navController = navController,
                    startDestination = if (auth.currentUser == null) AuthRoutes.GRAPH_ROUTE else HomeRoutes.GRAPH_ROUTE
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
        if (::briefingViewModel.isInitialized) briefingViewModel.refresh()
        if (::projectViewModel.isInitialized) projectViewModel.refresh()
    }
}