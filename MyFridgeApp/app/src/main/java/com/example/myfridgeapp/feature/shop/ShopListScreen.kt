package com.example.myfridgeapp.feature.shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myfridgeapp.R
import com.example.myfridgeapp.ui.theme.DeepGreen
import com.example.myfridgeapp.ui.theme.MintBlue
import com.example.myfridgeapp.ui.theme.MintWhite
import com.example.myfridgeapp.ui.theme.boxYellow
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopListScreen(navController: NavController) {

    val currentUser = FirebaseAuth.getInstance().currentUser
    val userEmail = currentUser?.email ?: ""

    val viewModel: ShopViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {
        viewModel.listenForShop(userEmail)
    }

    val shopList by viewModel.shopList.collectAsState(emptyList())

    var selectedButton by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MintBlue,
        topBar = {
            CenterAlignedTopAppBar( //글자가 가운데
                modifier = Modifier.clip(
                    RoundedCornerShape(
                        bottomStart = 16.dp,
                        bottomEnd = 16.dp
                    )
                ),
                colors = topAppBarColors(
                    containerColor = MintWhite,
                    titleContentColor = MintBlue
                ),
                title = {
                    Text(
                        text = "장바구니",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(vertical = 16.dp),
            //verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.button_add_manually),
                contentDescription = null,
                modifier = Modifier
                    .height(60.dp)
                    .clickable { navController.navigate("shopRegister") },
                contentScale = ContentScale.FillHeight
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(
                        id = if (selectedButton == 0) R.drawable.button_all_chosen else R.drawable.button_all
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .height(26.dp)
                        .weight(1f)
                        .clickable { selectedButton = 0 }
                )
                Image(
                    painter = painterResource(
                        id = if (selectedButton == 1) R.drawable.button_food_chosen else R.drawable.button_food
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .height(26.dp)
                        .weight(1f)
                        .clickable { selectedButton = 1 }
                )
                Image(
                    painter = painterResource(
                        id = if (selectedButton == 2) R.drawable.button_essentials_chosen else R.drawable.button_essentials
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .height(26.dp)
                        .weight(1f)
                        .clickable { selectedButton = 2 }
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                val filteredList = if (selectedButton == 0) {
                    shopList
                } else if (selectedButton == 1) {
                    shopList.filter { !it.eorf }
                } else {
                    shopList.filter { it.eorf }
                }

                items(filteredList) { item ->
                    var boxColor by remember { mutableStateOf(boxYellow) }

                    // 가격 비교 후 boxColor 바꾸기
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .background(boxColor)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo_shop),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(vertical = 5.dp, horizontal = 10.dp)
                                    .align(Alignment.CenterVertically)
                            )
                            Column(
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = item.name,
                                    color = DeepGreen,
                                    fontWeight = FontWeight.Bold
                                )
                                Image(
                                    painter = painterResource(id = R.drawable.mini_dotted_line),
                                    contentDescription = null
                                )
                                Text(
                                    text = if (item.eorf) "생필품" else "식재료",
                                    color = DeepGreen,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Box(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Button(
                                    onClick = { viewModel.deleteShop(item.id) },
                                    modifier = Modifier
                                        .size(30.dp),
                                    contentPadding = PaddingValues(0.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.button_delete),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(boxColor)
                                    )

                                }
                            }

                        }

                    }
                }

            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ShopListScreenPreview() {
    val context = LocalContext.current
    val mockNavController = remember { NavController(context) }

    ShopListScreen(navController = mockNavController)
}