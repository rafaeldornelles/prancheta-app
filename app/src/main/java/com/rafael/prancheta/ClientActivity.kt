package com.rafael.prancheta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.rafael.baseui.theme.PranchetaTheme
import com.rafael.featureauth.presentation.navigation.AuthRoutes
import com.rafael.featureauth.presentation.navigation.authGraph
import com.rafael.featurebriefing.presentation.navigation.BriefingRoutes
import com.rafael.featurebriefing.presentation.navigation.briefingGraph


class ClientActivity : ComponentActivity() {

    private val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Prancheta)
        setContent {
            PranchetaTheme {
                val navController = rememberNavController()
                if (auth.currentUser == null) { auth.signInAnonymously() }
                NavHost(
                    navController = navController,
                    startDestination = BriefingRoutes.NAV_ROUTE
                ) {
                    briefingGraph(navController)
                }
            }
        }
    }
}