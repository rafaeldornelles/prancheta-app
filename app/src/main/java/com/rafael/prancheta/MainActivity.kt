package com.rafael.prancheta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.rafael.featureauth.presentation.navigation.AuthRoutes
import com.rafael.featureauth.presentation.navigation.GRAPH_ROUTE
import com.rafael.featureauth.presentation.navigation.authGraph
import com.rafael.prancheta.ui.theme.PranchetaTheme

class MainActivity : ComponentActivity() {

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PranchetaTheme {
                val navController = rememberNavController()
                auth.addAuthStateListener {
                    if (it.currentUser == null) {
                        navController.navigate(AuthRoutes.Login.route) {
                            popUpTo(0)
                        }
                    }else {
                        navController.navigate("logged") {
                            popUpTo(0)
                        }
                    }
                }
               NavHost(navController = navController, startDestination = GRAPH_ROUTE) {
                   authGraph(navController)
                   composable("logged") {
                       BottomNavigationScreen()
                   }
               }
            }
        }
    }
}