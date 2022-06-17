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
import com.example.colunch.widgets.OutLineTextField
import android.app.TimePickerDialog
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import com.example.colunch.viewmodels.LunchideasModel
import kotlinx.coroutines.CoroutineScope
import java.util.*

@Composable
fun AddLunchScreen(navController: NavController,
                   restaurantViewModel: Restaurantsmodel,
                   scaffoldState: ScaffoldState,
                   scope: CoroutineScope,
                   appid: String,
                   onAddLunchideaClick: (List<String>) -> Unit ={},
                   ) {


    BottomTopBar(title = "Add LunchIdea", navController, scaffoldState, scope) {
        Card(modifier = Modifier.padding(10.dp)
        ) {

                Column(modifier = Modifier.padding(5.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    var name = OutLineTextField("Name")
                    var restaurant = DropDown(restaurantViewModel)
                    var mahlzeit = OutLineTextField("Bestellung")
                    var time = TimePicker()
                    var bezahlungsart = DropDownPayment()
                    /*    Log.d("AddLunchidea", "Name: " + name)
                        Log.d("AddLunchidea", "Restaurant: "+ restaurant)
                        Log.d("AddLunchidea", "Bestellung: " + bestellung)
                        Log.d("AddLunchidea", "Zeit: " + time)
                        Log.d("AddLunchidea", "Bezahlungsart: " + bezahlungsart)*/
                    Button(onClick = { onAddLunchideaClick(mutableListOf(name,restaurant,time,bezahlungsart,mahlzeit)) }) {
                        Text(text = "Add Lunchidea")
                    }
                }

        }
    }

}

@Composable
fun DropDown(restaurantViewModel: Restaurantsmodel) : String{

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
    return mSelectedText
}

@OptIn(ExperimentalGraphicsApi::class)
@Composable
fun TimePicker(): String {

    // Fetching local context
    val mContext = LocalContext.current

    // Declaring and initializing a calendar
    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    // Value for storing time as a string
    val mTime = remember { mutableStateOf("") }

    // Creating a TimePicker dialog
    val mTimePickerDialog = TimePickerDialog(
        mContext,
        {_, mHour : Int, mMinute: Int ->
            if(mMinute.toString().length !=1){
                mTime.value = "$mHour:$mMinute"
            }
            else{
                mTime.value = "$mHour:0$mMinute"
            }

        }, mHour, mMinute, false
    )

        OutlinedButton(onClick = { mTimePickerDialog.show() }, Modifier.padding(20.dp))
        {
            Text("Bestelluhrzeit: ${mTime.value}")
        }
    return mTime.value
}

@Composable
fun DropDownPayment() : String{

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
    return mSelectedText.toString()
}