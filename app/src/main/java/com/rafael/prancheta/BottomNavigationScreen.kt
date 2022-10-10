package com.rafael.prancheta

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.google.firebase.auth.FirebaseAuth

@Composable
fun BottomNavigationScreen() {
    val auth = FirebaseAuth.getInstance()
    Text(text = "Is logged")
    Button(onClick = { auth.signOut() }) {
        Text(text = "Log off")
    }
}