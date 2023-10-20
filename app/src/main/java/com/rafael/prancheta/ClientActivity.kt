package com.rafael.prancheta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.rafael.baseui.theme.PranchetaTheme
import com.rafael.featurebriefing.presentation.navigation.BriefingRoutes
import com.rafael.featurebriefing.presentation.navigation.briefingGraph
import com.rafael.featureproject.presentation.navigation.projectGraph
import org.koin.androidx.viewmodel.ext.android.getViewModel


class ClientActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Prancheta)
        setContent {
            PranchetaTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = BriefingRoutes.NAV_ROUTE
                ) {
                    briefingGraph(navController, getViewModel())
                    projectGraph(navController)
                }
            }
        }
    }
}