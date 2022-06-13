package com.example.colunch.widgets

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.colunch.models.Lunchidea
import com.example.colunch.models.addToFirestore
import com.example.colunch.screens.HomeScreen
import com.example.colunch.ui.theme.CoLunchTheme

@Composable
fun simpleTextField() : String {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    TextField(
        value = text,
        onValueChange = { newText ->
            text = newText
        }
    )
    return text.text
}

@Composable
fun SimpleButton(inputtext : String) {
    Button(onClick = {
        addToFirestore(inputtext)
    }) {
        Text(text = "Simple Button")
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomTopBar(

    title : String,
    content: @Composable () -> Unit = {}
) {
    CoLunchTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val selectedItem = remember { mutableStateOf("upload")}

            Scaffold(topBar = {

                TopAppBar(
                    elevation = 3.dp
                ){
                    Row{
                        if(title != "Lunch Ideas") {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "ArrowBack",
                                modifier = Modifier.clickable {  /*navController.popBackStack() */ },
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(15.dp))
                        }

                        Text(text = title, style = MaterialTheme.typography.h6, color = Color.White)




                    }
                }

            },
                bottomBar = {
                    BottomAppBar(
                        content = {
                            BottomNavigation() {

                                BottomNavigationItem(
                                    icon = {
                                        Icon(Icons.Filled.Menu , "")
                                    },
                                    label = { Text(text = "Menu")},
                                    selected = selectedItem.value == "menu",
                                    onClick = {
                                        //result.value = "Save icon clicked"
                                        //selectedItem.value = "save"
                                    },
                                    alwaysShowLabel = false
                                )

                                BottomNavigationItem(
                                    icon = {
                                        Icon(Icons.Filled.Add ,  "")
                                    },


                                    label = { Text(text = "New Idea")},
                                    selected = selectedItem.value == "addidea",
                                    onClick = {
                                        //result.value = "Upload icon clicked"
                                        //selectedItem.value = "upload"
                                    },
                                    alwaysShowLabel = false
                                )

                                BottomNavigationItem(
                                    icon = {
                                        Icon(Icons.Filled.Place , "")
                                    },
                                    label = { Text(text = "Restaurants")},


                                    selected = selectedItem.value == "restaurants",
                                    onClick = {
                                        //result.value = "Download icon clicked"
                                        //selectedItem.value = "download"
                                    },
                                    alwaysShowLabel = false
                                )
                            }
                        }
                    )

                })
            {
                content()

            }
        }
    }

}
@ExperimentalAnimationApi
@Composable
fun LunchideaRow(lunchidea: Lunchidea,
             onItemClick: (String) -> Unit = {},
             content: @Composable () -> Unit = {}
) {

    var expandMovieRow by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable { },

        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = 6.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(6.dp),
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                //horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(6.dp),
            ) {
                Surface(
                    modifier = Modifier
                        .size(95.dp)
                        .padding(3.dp),
                ) {

                }
                Column {
                    Text(text = lunchidea.restaurant, style = MaterialTheme.typography.h6)
                    Text("Zeit: ${lunchidea.bestellzeit}", style = MaterialTheme.typography.body2)

                }

            }
            content()
        }
    }
}



@Composable
fun Greeting(name: String) {
    Text(text = name)
}