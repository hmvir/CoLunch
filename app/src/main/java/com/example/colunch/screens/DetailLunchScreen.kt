package com.example.colunch.screens


import android.widget.EditText
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.colunch.models.Lunchidea
import com.example.colunch.widgets.BottomTopBar
import com.example.colunch.widgets.LunchDetails
import com.example.colunch.widgets.simpleTextField

@Composable
fun LunchIdeaDescription(
    lunchidea: Lunchidea
) {
    BottomTopBar(title = lunchidea.restaurant + " " + lunchidea.bestellzeit)
    LunchDetails(lunchidea = lunchidea)



}

/*
fun LunchIdeaDescription(
    lunchidea: Lunchidea
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        color = MaterialTheme.colors.background
    ) {
        Scaffold(topBar = {
            TopAppBar(
                title = { Text("Detailansicht") }
            )
            Row {
                Text(text = "Text") // Beschreibung der Lunchidee
                var order = simpleTextField()

            }
        }){

        }
    }

*/
