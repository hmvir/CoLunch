package com.example.colunch.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.colunch.widgets.*

@Composable
fun OrderScreen(
    navController: NavHostController,
    name: String,
    type: String,
    scaffoldState : ScaffoldState,
    onClick: (ArrayList<String>) -> Unit = {}
) {
    BottomTopBar(
        title = type +" Order to Lunchidea",
        navController,
        scaffoldState = rememberScaffoldState()
    ) {
        MainContent(name, type, scaffoldState) { orderlist ->
            onClick(orderlist)
        }


    }
}

@Composable
fun MainContent(
    name: String,
    type: String,
    scaffoldState : ScaffoldState,
    onClick: (ArrayList<String>) -> Unit = {}
) {
    Column() {
        var buyer by rememberSaveable { mutableStateOf("") }
        if (name.isNullOrEmpty()) {
            Log.d("OrderScreen", "Add")
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = buyer,
                onValueChange = { buyer = it },
                label = { Text("Name") },
                singleLine = true
            )
        } else {
            Log.d("OrderScreen", "Update")
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                onValueChange = { buyer = it },
                enabled = false,
                label = { Text("Name") },
                singleLine = true
            )
        }


        var order by rememberSaveable { mutableStateOf("") }
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = order,
            onValueChange = { order = it },
            label = { Text("Bestellung") },
            singleLine = false
        )


        Button(inputtext = type+" Order to Lunchidea") {
                onClick(arrayListOf(buyer, order)

                )
            }

        }

        //SnackbarHost(hostState = snackbarHostState)
    }


