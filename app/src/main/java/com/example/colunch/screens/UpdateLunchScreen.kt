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
fun UpdateLunchScreen(navController: NavController,
                   lunchideasModel: LunchideasModel,
                   lunchId: String = "",
                   scaffoldState: ScaffoldState,
                   scope: CoroutineScope,
                   onUpdateLunchideaClick: (String) -> Unit ={},
) {


    BottomTopBar(title = "Update LunchIdea", navController, scaffoldState, scope) {
        Card(modifier = Modifier.padding(10.dp)
        ) {

            Column(modifier = Modifier.padding(5.dp), horizontalAlignment = Alignment.CenterHorizontally) {


                    var lunchidea = lunchideasModel.getLunchIdea(lunchId)
                    OutlinedTextField(
                        value = lunchidea.restaurant,
                        enabled = false,
                        label = { Text(text = "Restaurant") },
                        onValueChange = {}
                    )
                    var time = TimePicker()
                    OutlinedTextField(
                        value = lunchidea.bezahlungsart,
                        enabled = false,
                        label = { Text(text = "Zahlungsart") },
                        onValueChange = {}
                    )
                    Button(
                        modifier = Modifier.padding(10.dp),
                        onClick = { onUpdateLunchideaClick(time) }) {
                        Text(text = "Update Lunchidea")
                    }
                }
            }
        }
    }



