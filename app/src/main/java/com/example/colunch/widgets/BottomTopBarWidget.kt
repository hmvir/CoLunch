package com.example.colunch.widgets

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.colunch.navigation.Screens
import com.example.colunch.ui.theme.CoLunchTheme


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomTopBar(
    title: String,
    navController: NavController,
    content: @Composable () -> Unit = {}
) {
    CoLunchTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),

            color = MaterialTheme.colors.background
        ) {
            val selectedItem = remember { mutableStateOf("upload") }

            Scaffold(topBar = {

                TopAppBar(
                    elevation = 3.dp
                ) {
                    Row {
                        if (title != "Lunch Ideas") {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "ArrowBack",
                                modifier = Modifier.clickable { navController.popBackStack() },
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(15.dp))
                        }

                        Text(
                            text = title,
                            style = MaterialTheme.typography.h6,
                            color = Color.White
                        )


                    }
                }

            },
                bottomBar = {
                    BottomAppBar(
                        content = {
                            BottomNavigation() {

                                BottomNavigationItem(
                                    icon = {
                                        Icon(Icons.Filled.Menu, "")
                                    },
                                    label = { Text(text = "Menu") },
                                    selected = selectedItem.value == "menu",
                                    onClick = {
                                        //result.value = "Save icon clicked"
                                        //selectedItem.value = "save"
                                    },
                                    alwaysShowLabel = false
                                )

                                BottomNavigationItem(
                                    icon = {
                                        Icon(Icons.Filled.Add, "")
                                    },


                                    label = { Text(text = "New Idea") },
                                    selected = selectedItem.value == "addidea",
                                    onClick = {
                                        navController.navigate(Screens.AddLunchscreen.name)
                                    },
                                    alwaysShowLabel = false
                                )

                                BottomNavigationItem(
                                    icon = {
                                        Icon(Icons.Filled.Place, "")
                                    },
                                    label = { Text(text = "Restaurants") },


                                    selected = selectedItem.value == "restaurants",
                                    onClick = {
                                        navController.navigate(Screens.Restaurantsscreen.name)
                                    },
                                    alwaysShowLabel = false
                                )
                            }
                        }
                    )

                })
            {innerPadding ->
                // Apply the padding globally to the whole BottomNavScreensController
                Box(modifier = Modifier.padding(innerPadding)) {
                    content()
                }
            }
        }
    }

}