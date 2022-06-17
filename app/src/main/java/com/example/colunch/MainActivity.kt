package com.example.colunch

import android.os.Bundle
import android.util.Log
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
            val appid = getRandomString(20)
            val pref = getSharedPreferences("MYAPPKEY", 0)
            val returnid = pref.getString("Test", "")
            if(returnid.isNullOrEmpty()){
                val ed = pref.edit()
                ed.putString("Test", appid)
                ed.commit()
            }
             // oder ed.apply();



            Log.d("Test", returnid.toString())

            MyNavigation(scaffoldState, returnid.toString())
        }
    }
}

fun getRandomString(length: Int) : String {
    val charset = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz0123456789"
    return (1..length)
        .map { charset.random() }
        .joinToString("")
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CoLunchTheme {
        simpleTextField()
    }
}