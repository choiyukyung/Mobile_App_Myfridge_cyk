package com.example.myfridgeapp.feature.essentials

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myfridgeapp.R
import com.example.myfridgeapp.ui.CustomCurvedTopAppBar
import com.example.myfridgeapp.ui.CustomOutlinedTextField
import com.example.myfridgeapp.ui.CustomRegisterButton
import com.example.myfridgeapp.ui.theme.MintBlue
import com.google.firebase.auth.FirebaseAuth

@Composable
fun EssentialsRegisterScreen(navController: NavController) {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val userEmail = currentUser?.email ?: ""

    val viewModel: EssentialsViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {
        viewModel.listenForEssentials(userEmail)
    }

    var name by remember { mutableStateOf("") }
    var place by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    //success/fail message
    val uiState = viewModel.state.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(key1 = uiState.value) {
        when (uiState.value) {
            is EssentialsState.Success -> {
                Toast.makeText(context, "Register Success", Toast.LENGTH_SHORT).show()
                navController.navigate("essentialsRegister")
            }

            is EssentialsState.Error -> {
                Toast.makeText(context, "Register Failed", Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MintBlue,
        topBar = {
            CustomCurvedTopAppBar(
                titleText = stringResource(id = R.string.eRegister),
                onNavigationClick = { navController.navigate("addNewItem") }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp, vertical = 48.dp)
                .background(color = Color.White, shape = RoundedCornerShape(size = 50.dp)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.register_in_cupboard),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                )
                Spacer(modifier = Modifier.size(32.dp))
                CustomOutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = stringResource(id = R.string.whatsName)
                )
                Spacer(modifier = Modifier.size(32.dp))
                CustomOutlinedTextField(
                    value = place,
                    onValueChange = { place = it },
                    label = stringResource(id = R.string.wheresPlace)
                )
                Spacer(modifier = Modifier.size(32.dp))
                CustomOutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = stringResource(id = R.string.howsPrice)
                )
                Spacer(modifier = Modifier.size(32.dp))
                CustomRegisterButton(
                    text = stringResource(id = R.string.register),
                    onClick = { viewModel.addEssentials(userEmail, name, place, price) },
                    enabled = true
                )
            }

        }
    }
}
