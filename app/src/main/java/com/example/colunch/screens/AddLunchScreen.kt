package com.example.colunch.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.colunch.viewmodels.Restaurantsmodel
import com.example.colunch.widgets.BottomTopBar
import com.example.colunch.widgets.OutLineTextFieldSample
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import java.util.*

@Composable
fun AddLunchScreen(navController: NavController, restaurantViewModel: Restaurantsmodel) {
    var mExpanded by remember { mutableStateOf(true) }
    var mSelectedText by remember { mutableStateOf("") }
    var mTextFieldSize by remember { mutableStateOf(0)}
    var restaurants = restaurantViewModel.getRestaurants()

    BottomTopBar(title = "Add LunchIdea", navController, scaffoldState = rememberScaffoldState()) {
        Card(modifier = Modifier.padding(10.dp)
        ) {
            Column(modifier = Modifier.padding(5.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                OutLineTextFieldSample("Name")
                DropDown(restaurantViewModel)
                OutLineTextFieldSample("Bestellung")
                TimePicker()
                DropDownPayment()
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

@OptIn(ExperimentalGraphicsApi::class)
@Composable
fun TimePicker(){

    // Fetching local context
    val mContext = LocalContext.current

    // Declaring and initializing a calendar
    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    // Value for storing time as a string
    val mTime = remember { mutableStateOf("") }

    // Creating a TimePicker dialod
    val mTimePickerDialog = TimePickerDialog(
        mContext,
        {_, mHour : Int, mMinute: Int ->
            mTime.value = "$mHour:$mMinute"
        }, mHour, mMinute, false
    )

        OutlinedButton(onClick = { mTimePickerDialog.show() }, Modifier.padding(20.dp))
        {
            Text("Bestelluhrzeit: ${mTime.value}")
        }
}

@Composable
fun DropDownPayment(){

    // Declaring a boolean value to store
    // the expanded state of the Text Field
    var mExpanded by remember { mutableStateOf(false) }
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
            enabled = false,
            label = {Text("Zahlungsart")},
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
            DropdownMenuItem(onClick = {
                mSelectedText = "Bar"
                mExpanded = false
            }) {
                Text(text = "Bar")
            }


            DropdownMenuItem(onClick = {
                mSelectedText = "Überweisung"
                mExpanded = false
            }) {
                Text(text = "Überweisung")
            }

            DropdownMenuItem(onClick = {
                mSelectedText = "Paypal"
                mExpanded = false
            }) {
                Text(text = "Paypal")
            }
        }
    }
}