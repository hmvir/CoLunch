package com.example.colunch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.colunch.navigation.MyNavigation
import com.example.colunch.ui.theme.CoLunchTheme
import com.example.colunch.widgets.simpleTextField

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scaffoldState = rememberScaffoldState()
            MyNavigation(scaffoldState)
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