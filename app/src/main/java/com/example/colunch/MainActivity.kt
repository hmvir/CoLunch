package com.example.colunch

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.colunch.models.Restaurant
import com.example.colunch.models.getRestaurantsFromFirestore
import com.example.colunch.navigation.MyNavigation
import com.example.colunch.ui.theme.CoLunchTheme
import com.example.colunch.widgets.Greeting
import com.example.colunch.widgets.simpleTextField

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyNavigation()
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CoLunchTheme {
        simpleTextField()
    }
}