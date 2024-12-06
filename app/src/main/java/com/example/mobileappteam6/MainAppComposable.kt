package com.example.mobileappteam6

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobileappteam6.feature.auth.signin.SignInScreen
import com.example.mobileappteam6.feature.auth.signup.SignUpScreen
import com.example.mobileappteam6.feature.essentials.EssentialsListScreen
import com.example.mobileappteam6.feature.essentials.EssentialsRegisterScreen
import com.example.mobileappteam6.feature.home.HomeScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainApp() {

    Surface(modifier = Modifier.fillMaxSize()) {
        val navController = rememberNavController()
        val currentUser = FirebaseAuth.getInstance().currentUser
        val start = if (currentUser != null) "home" else "login"

        NavHost(navController = navController, startDestination = start) {
            composable(route = "login") {
                SignInScreen(navController = navController)
            }
            composable(route = "signup") {
                SignUpScreen(navController = navController)
            }
            composable(route = "home") {
                HomeScreen(navController = navController)
            }

            composable(route = "essentialsRegister") {
                EssentialsRegisterScreen(navController = navController)
            }
            composable(route = "essentialsList") {
                EssentialsListScreen(navController = navController)
            }
        }
    }
}