package com.example.colunch.screens

import android.util.Size
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.example.colunch.viewmodels.Restaurantsmodel
import com.example.colunch.widgets.BottomTopBar
import com.example.colunch.widgets.OutLineTextFieldSample
import com.example.colunch.widgets.simpleTextField

@Composable
fun AddLunchScreen(navController: NavController, restaurantViewModel: Restaurantsmodel) {
    var mExpanded by remember { mutableStateOf(true) }
    var mSelectedText by remember { mutableStateOf("") }
    var mTextFieldSize by remember { mutableStateOf(0)}
    var restaurants = restaurantViewModel.getRestaurants()

    BottomTopBar(title = "Add LunchIdea",navController) {
        Card(modifier = Modifier.padding(10.dp)
        ) {
            Column(modifier = Modifier.padding(5.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                DropDown(restaurantViewModel)
                OutLineTextFieldSample("Bestellzeit")
            }
        }
    }

}

@Composable
fun DropDown(restaurantViewModel: Restaurantsmodel){

    // Declaring a boolean value to store
    // the expanded state of the Text Field
    var mExpanded by remember { mutableStateOf(false) }
    val restaurants = restaurantViewModel.getRestaurants()
    var mSelectedText by remember { mutableStateOf("") }

    // Up Icon when expanded and down icon when collapsed
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(Modifier.padding(20.dp)) {

        // Create an Outlined Text Field
        // with icon and not expanded
        OutlinedTextField(
            value = mSelectedText,
            onValueChange = { mSelectedText = it },
            modifier = Modifier
                .fillMaxWidth()
                ,
            enabled = false,
            label = {Text("Restaurant")},
            trailingIcon = {
                Icon(icon,"contentDescription",
                    Modifier.clickable { mExpanded = !mExpanded })
            }
        )

        // Create a drop-down menu with list of cities,
        // when clicked, set the Text Field text as the city selected
        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            restaurants.forEach { restaurant ->
                DropdownMenuItem(onClick = {
                    mSelectedText = restaurant.name
                    mExpanded = false
                }) {
                    Text(text = restaurant.name)
                }
            }
        }
    }
}