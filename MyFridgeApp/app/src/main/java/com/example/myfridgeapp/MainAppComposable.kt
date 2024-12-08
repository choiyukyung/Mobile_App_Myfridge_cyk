package com.example.myfridgeapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myfridgeapp.feature.auth.signin.SignInScreen
import com.example.myfridgeapp.feature.auth.signup.SignUpScreen
import com.example.myfridgeapp.feature.essentials.EssentialsListScreen
import com.example.myfridgeapp.feature.essentials.EssentialsRegisterScreen
import com.example.myfridgeapp.feature.food.FoodListScreen
import com.example.myfridgeapp.feature.food.FoodRegisterScreen
import com.example.myfridgeapp.feature.home.AddNewItemScreen
import com.example.myfridgeapp.feature.home.HomeScreen
import com.example.myfridgeapp.feature.shop.ShopListScreen
import com.example.myfridgeapp.feature.shop.ShopRegisterScreen
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

            composable(route = "addNewItem") {
                AddNewItemScreen(navController = navController)
            }

            composable(route = "essentialsRegister") {
                EssentialsRegisterScreen(navController = navController)
            }
            composable(route = "essentialsList") {
                EssentialsListScreen(navController = navController)
            }

            composable(route = "foodRegister") {
                FoodRegisterScreen(navController = navController)
            }
            composable(route = "foodList") {
                FoodListScreen(navController = navController)
            }

            composable(route = "shopList") {
                ShopListScreen(navController = navController)
            }
            composable(route = "shopRegister") {
                ShopRegisterScreen(navController = navController)
            }
        }
    }
}