package com.example.colunch.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {

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

        }
    }
}