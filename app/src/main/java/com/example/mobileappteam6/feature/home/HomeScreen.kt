package com.example.mobileappteam6.feature.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val uiState = viewModel.state.collectAsState()

    LaunchedEffect (key1 = uiState.value){
        if(uiState.value == SignOutState.LoggedOut) {
            navController.navigate("login") {
                popUpTo("home") { inclusive = true }
            }
        }

    }

    Text(text = "home page")
    IconButton(onClick = { viewModel.signOut() }) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
            contentDescription = null,
            tint = Color.Gray
        )
    }
}