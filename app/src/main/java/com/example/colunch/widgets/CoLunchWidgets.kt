package com.example.colunch.widgets

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.colunch.models.Lunchidea
import com.example.colunch.models.Restaurant
import com.example.colunch.models.addTeilnehmerLunchideaToFirebase
import com.example.colunch.navigation.Screens
import com.example.colunch.ui.theme.CoLunchTheme
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun simpleTextField(): String {
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
fun OutLineTextFieldSample(inputtext: String) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    OutlinedTextField(
        value = text,
        label = { Text(text = inputtext) },
        onValueChange = {
            text = it
        }
    )
}

@Composable
fun SimpleButton(
    inputtext: String,
    transaction: String,
    arrayListOf: ArrayList<String>,
    db: FirebaseFirestore,
    id: String
) {
    Button(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),

        onClick = {
            if (transaction == "order") {
                addTeilnehmerLunchideaToFirebase(db, id, arrayListOf.get(0), arrayListOf.get(1))
            } else {

            }

        }) {
        Text(text = inputtext)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomTopBar(
    title: String,
    navController: NavController,
    content: @Composable () -> Unit = {}
) {
    CoLunchTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val selectedItem = remember { mutableStateOf("upload") }

            Scaffold(topBar = {

                TopAppBar(
                    elevation = 3.dp
                ) {
                    Row {
                        if (title != "Lunch Ideas") {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "ArrowBack",
                                modifier = Modifier.clickable { navController.popBackStack() },
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(15.dp))
                        }

                        Text(
                            text = title,
                            style = MaterialTheme.typography.h6,
                            color = Color.White
                        )


                    }
                }

            },
                bottomBar = {
                    BottomAppBar(
                        content = {
                            BottomNavigation() {

                                BottomNavigationItem(
                                    icon = {
                                        Icon(Icons.Filled.Menu, "")
                                    },
                                    label = { Text(text = "Menu") },
                                    selected = selectedItem.value == "menu",
                                    onClick = {
                                        //result.value = "Save icon clicked"
                                        //selectedItem.value = "save"
                                    },
                                    alwaysShowLabel = false
                                )

                                BottomNavigationItem(
                                    icon = {
                                        Icon(Icons.Filled.Add, "")
                                    },


                                    label = { Text(text = "New Idea") },
                                    selected = selectedItem.value == "addidea",
                                    onClick = {
                                        navController.navigate(Screens.AddLunchscreen.name)
                                    },
                                    alwaysShowLabel = false
                                )

                                BottomNavigationItem(
                                    icon = {
                                        Icon(Icons.Filled.Place, "")
                                    },
                                    label = { Text(text = "Restaurants") },


                                    selected = selectedItem.value == "restaurants",
                                    onClick = {
                                        navController.navigate(Screens.Restaurantsscreen.name)
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
fun LunchideaRow(
    lunchidea: Lunchidea,
    onItemClick: (String) -> Unit = {},
    content: @Composable () -> Unit = {}
) {

    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable { onItemClick(lunchidea.id) },

        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = 6.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(6.dp),
        ) {
            Surface(
                modifier = Modifier
                    .size(95.dp)
                    .padding(3.dp),
            ) {
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
fun LunchDetails(
    lunchidea: Lunchidea,
    db: FirebaseFirestore,
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            ,

        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = 6.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(6.dp),
        ) {

            Surface(
                modifier = Modifier
                    .padding(3.dp)
            ) {
                Column {

                    Text(text = lunchidea.restaurant, style = MaterialTheme.typography.h6)
                    Text("Zeit: ${lunchidea.bestellzeit}", style = MaterialTheme.typography.body2)
                    Text(text = lunchidea.bezahlungsart, style = MaterialTheme.typography.h2)

                    for (teilnehmer in lunchidea.teilnehmer) {
                        Text(teilnehmer.getValue("Name"))
                        Text(teilnehmer.getValue("Mahlzeit"))

                    }

                    Order(db, lunchidea.id)


                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun RestaurantRow(
    restaurant: Restaurant,
    content: @Composable () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable { },

        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = 6.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(6.dp),
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(6.dp),
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(3.dp),
                ) {
                    Column {
                        Text(text = restaurant.name, style = MaterialTheme.typography.h6)
                        Text(text = restaurant.beschreibung, style = MaterialTheme.typography.body2)
                        Hyperlink(restaurant.website)
                    }
                }
            }
            content()
        }
    }
}

@Composable
fun Hyperlink(link: String) {
    val context = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(link)) }

    Button(onClick = { context.startActivity(intent) }) {
        Text(text = "Go to website!")
    }
}

@Composable
fun Order(db: FirebaseFirestore, id: String) {
    var buyer by rememberSaveable { mutableStateOf("")}
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = buyer,
            onValueChange = { buyer = it },
            label = { Text("Name") },
            singleLine = true
        )
    var order by rememberSaveable { mutableStateOf("")}
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = order,
            onValueChange = { order = it },
            label = { Text("Bestellung") },
            singleLine = false
    )
    SimpleButton(inputtext = "Bestellung hinzufügen", "order", arrayListOf<String>(buyer, order), db, id)



}

@Composable
fun Greeting(name: String) {
    Text(text = name)
}