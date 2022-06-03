package com.example.colunch.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.colunch.Greeting
import com.example.colunch.ui.theme.CoLunchTheme

@Composable
fun HomeScreen(
    navController: NavController,
    FavoritViewModel: ViewModel,
    fromFirestore: List<String>
) {

    CoLunchTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val selectedItem = remember { mutableStateOf("upload")}

            Scaffold(topBar = {
                TopAppBar(
                    title = { Text("Lunch Ideas") }
                )},
                bottomBar = {
                    BottomAppBar(
                        content = {
                            BottomNavigation() {

                                BottomNavigationItem(
                                    icon = {
                                        Icon(Icons.Filled.Menu , "")
                                    },
                                    label = { Text(text = "Menu")},
                                    selected = selectedItem.value == "menu",
                                    onClick = {
                                        //result.value = "Save icon clicked"
                                        //selectedItem.value = "save"
                                    },
                                    alwaysShowLabel = false
                                )

                                BottomNavigationItem(
                                    icon = {
                                        Icon(Icons.Filled.Add ,  "")
                                    },


                                    label = { Text(text = "New Idea")},
                                    selected = selectedItem.value == "addidea",
                                    onClick = {
                                        //result.value = "Upload icon clicked"
                                        //selectedItem.value = "upload"
                                    },
                                    alwaysShowLabel = false
                                )

                                BottomNavigationItem(
                                    icon = {
                                        Icon(Icons.Filled.Place , "")
                                    },
                                    label = { Text(text = "Restaurants")},


                                    selected = selectedItem.value == "restaurants",
                                    onClick = {
                                        //result.value = "Download icon clicked"
                                        //selectedItem.value = "download"
                                    },
                                    alwaysShowLabel = false
                                )
                            }
                        }
                    )

                })
            {
                LazyColumn {
                    items(fromFirestore) { movie ->
                        Greeting(name = movie)
                    }
                }
            }
        }
    }

}