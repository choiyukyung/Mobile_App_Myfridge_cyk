package com.example.myfridgeapp.feature.home

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myfridgeapp.R
import com.example.myfridgeapp.ui.theme.DeepGreen
import com.example.myfridgeapp.ui.theme.MintBlue
import com.example.myfridgeapp.ui.theme.MintWhite

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val uiState = viewModel.state.collectAsState()

    LaunchedEffect(key1 = uiState.value) {
        if (uiState.value == SignOutState.LoggedOut) {
            navController.navigate("login") {
                popUpTo("home") { inclusive = true }
            }
        }

    }

    var selectedButton by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MintWhite,
        contentColor = MintWhite,
        topBar = {
            Column {
                TopAppBar(
                    modifier = Modifier.height(100.dp),
                    colors = TopAppBarDefaults.topAppBarColors(MintWhite),
                    title = {
                        Image(
                            painter = painterResource(id = R.drawable.logo_myfridge),
                            contentDescription = "image description",
                            modifier = Modifier.size(100.dp)
                        )
                    },
                    actions = {
                        IconButton(onClick = { viewModel.signOut() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = "Logout",
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    }
                )
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color.Gray
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                ButtonGroup(
                    navController = navController,
                    selectedButton = selectedButton,
                    onClick = { selectedButton = it }
                )

            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ocr_camera),
                contentDescription = null,
                modifier = Modifier
                    .size(160.dp)
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .clickable {
                        navController.navigate("addNewItem")
                    }
            )
        }
    }
}

@Composable
fun ButtonGroup(
    navController: NavController,
    selectedButton: Int?,
    onClick: (Int?) -> Unit
) {
    val buttons = listOf(1, 2, 3, 4)
    val buttonLabels = listOf("식재료\n모아보기", "생필품\n모아보기", "장보기\n계획하기", "레시피\n아이디어")
    val buttonImages = listOf(
        R.drawable.logo_food,
        R.drawable.logo_essentials,
        R.drawable.logo_shop,
        R.drawable.logo_kitchen
    )

    val animationOffsets = buttons.map { buttonLabel ->
        animateDpAsState(targetValue = if (selectedButton == buttonLabel) (-80).dp else (-180).dp)
    }

    buttons.forEachIndexed { index, buttonLabel ->
        Button(
            onClick = {
                if (selectedButton == buttonLabel) {
                    when (buttonLabel) {
                        1 -> navController.navigate("page1")

                        2 -> navController.navigate("essentialsList") {
                            popUpTo("home") { inclusive = true }
                        }

                        3 -> navController.navigate("page3")
                        4 -> navController.navigate("page4")
                    }
                    onClick(null)
                } else {
                    onClick(buttonLabel)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .padding(horizontal = 30.dp)
                .padding(vertical = 12.dp)
                .offset(x = animationOffsets[index].value),
            colors = ButtonDefaults.buttonColors(containerColor = MintBlue),
            shape = RoundedCornerShape(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 5.dp, horizontal = 20.dp)
                    .background(
                        color = MintWhite,
                        shape = RoundedCornerShape(
                            topStart = 120.dp,
                            topEnd = 20.dp,
                            bottomStart = 20.dp,
                            bottomEnd = 20.dp
                        )
                    ),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mini_fridge),
                    contentDescription = "image description",
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.BottomStart)
                )

                Text(
                    text = buttonLabels[index],
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(horizontal = 36.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepGreen,
                    lineHeight = 30.sp
                )

                Image(
                    painter = painterResource(id = buttonImages[index]),
                    contentDescription = "image description",
                    modifier = Modifier
                        .size(60.dp)
                        .align(Alignment.BottomEnd)
                        .offset(x = 45.dp)
                )


            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ButtonGroupPreview() {
    val context = LocalContext.current
    val mockNavController = remember { NavController(context) }

    ButtonGroup(mockNavController, 1, onClick = {})
}
