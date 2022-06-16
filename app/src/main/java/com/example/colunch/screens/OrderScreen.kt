package com.example.colunch.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.colunch.models.Lunchidea
import com.example.colunch.widgets.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

@Composable
fun OrderScreen(navController: NavHostController,
                name: String,
                onClick:(ArrayList<String>) -> Unit={}
                   ) {
    BottomTopBar(title =  "Add Order to Lunchidea", navController) {
        MainContent(name){ orderlist ->
            onClick(orderlist)
        }


    }
}

@Composable
fun MainContent(
    name: String,
                onClick:(ArrayList<String>) -> Unit={}
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
        }else{
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

        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }



            Button(inputtext = "Add Order to Lunchidea"){
                scope.launch {
                    onClick(arrayListOf(buyer,order))
                    snackbarHostState.showSnackbar("Bestellung erfasst")
                }

            }

        SnackbarHost(hostState = snackbarHostState)
    }


}