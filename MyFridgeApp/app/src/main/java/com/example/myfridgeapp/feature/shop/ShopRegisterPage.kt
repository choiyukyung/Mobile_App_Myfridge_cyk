package com.example.myfridgeapp.feature.shop

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myfridgeapp.R
import com.example.myfridgeapp.ui.theme.MintBlue
import com.example.myfridgeapp.ui.theme.MintWhite
import com.example.myfridgeapp.ui.theme.MintWhiteLight
import com.example.myfridgeapp.ui.theme.fontMint
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopRegisterScreen(navController: NavController) {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val userEmail = currentUser?.email ?: ""

    val viewModel: ShopViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {
        viewModel.listenForShop(userEmail)
    }

    var name by remember { mutableStateOf("") }
    var expDate by remember { mutableStateOf("") }
    var place by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    //success/fail message
    val uiState = viewModel.state.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(key1 = uiState.value) {
        when (uiState.value) {
            is ShopState.Success -> {
                Toast.makeText(context, "Register Success", Toast.LENGTH_SHORT).show()
                navController.navigate("shopRegister")
            }

            is ShopState.Error -> {
                Toast.makeText(context, "Register Failed", Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MintBlue,
        topBar = {
            TopAppBar(
                modifier = Modifier.clip(
                    RoundedCornerShape(
                        bottomStart = 16.dp,
                        bottomEnd = 16.dp
                    )
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("shopList") }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                title = {
                    Text(
                        text = stringResource(id = R.string.sRegister),
                        color = fontMint,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = topAppBarColors(MintWhite)

            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(vertical = 36.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_shop_circle),
                contentDescription = null,
                modifier = Modifier.size(230.dp)
            )
            Spacer(modifier = Modifier.size(32.dp))
            Image(
                painter = painterResource(id = R.drawable.dotted_line),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(11.dp)
            )
            Spacer(modifier = Modifier.size(32.dp))
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp)
                    .background(color = MintWhiteLight, shape = RoundedCornerShape(size = 10.dp)),
            ) {
                Spacer(modifier = Modifier.size(32.dp))
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.text_item_name),
                        contentDescription = null,
                        modifier = Modifier
                            .height(20.dp)
                    )
                }
                Spacer(modifier = Modifier.size(16.dp))
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(999.dp)),
                    placeholder = { Text(text = stringResource(id = R.string.whatsName)) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = MintWhite,
                        disabledBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        unfocusedPlaceholderColor = fontMint,
                        focusedPlaceholderColor = fontMint
                    )
                )
                Spacer(modifier = Modifier.size(16.dp))
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.padding(horizontal = 32.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.text_item_eorf),
                        contentDescription = null,
                        modifier = Modifier
                            .height(20.dp)
                    )
                }
                Spacer(modifier = Modifier.size(16.dp))
                Row (
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.button_choose_food),
                        contentDescription = null,
                        modifier = Modifier
                            .height(45.dp)
                            .weight(1f)
                            .clickable {}
                    )
                    Image(
                        painter = painterResource(id = R.drawable.button_choose_essentials),
                        contentDescription = null,
                        modifier = Modifier
                            .height(45.dp)
                            .weight(1f)
                            .clickable { }
                    )
                }

                Spacer(modifier = Modifier.size(32.dp))
            }
            Image(
                painter = painterResource(id = R.drawable.button_register_shop),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(120.dp)
                    .clickable {
                        /////////////////////////////////////////////
                        viewModel.addShop(userEmail, name, expDate, place, price, true)
                    }
            )


        }
    }
}
