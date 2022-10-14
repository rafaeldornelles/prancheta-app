package com.rafael.prancheta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.rafael.featureauth.presentation.navigation.AuthRoutes
import com.rafael.featureauth.presentation.navigation.authGraph
import com.rafael.featurebriefing.presentation.navigation.briefingGraph
import com.rafael.baseui.theme.PranchetaTheme

class MainActivity : ComponentActivity() {

    private val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Prancheta)
        setContent {
            PranchetaTheme {
                val navController = rememberNavController()
                auth.addAuthStateListener {
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
                    homeGraph(navController)
                    briefingGraph(navController)
                }
            }
        }
    }
}