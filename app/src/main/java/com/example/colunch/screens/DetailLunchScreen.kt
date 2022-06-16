package com.example.colunch.screens


import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.colunch.models.Lunchidea
import com.example.colunch.navigation.Screens
import com.example.colunch.viewmodels.LunchideasModel
import com.example.colunch.widgets.BottomTopBar
import com.example.colunch.widgets.Button
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun DetailLunchScreen(
    //lunchidea: Lunchidea,
    db: FirebaseFirestore,
    navController: NavHostController,
    lunchId: String?,
    lunchViewModel: LunchideasModel,
) {
    var lunchIdea = lunchId?.let { lunchViewModel.getLunchIdea(it) }
    if (lunchIdea != null) {
        BottomTopBar(title = lunchIdea.restaurant + ' ' + lunchIdea.bestellzeit, navController) {
            Log.d("lunch detail", lunchId.toString())
            LunchDetails(navController,
                lunchIdea,
                db,
                onUpdateOrderClick = {name -> navController.navigate(Screens.Orderscreen.name + "?lunchId=$lunchId&name=$name&type=update") }
            ) {
                Button(inputtext = "Add Order") {
                    navController.navigate(Screens.Orderscreen.name + "?lunchId=$lunchId&type=add")
                }
            }


        }

    }
}


@Composable
fun LunchDetails(
    navController: NavController,
    lunchidea: Lunchidea,
    db: FirebaseFirestore,
    onUpdateOrderClick: (String) -> Unit ={},
    content: @Composable () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .fillMaxHeight(),

        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = 6.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(6.dp)

        ) {


            Column(
            ) {

                Text(text = lunchidea.restaurant, style = MaterialTheme.typography.h6)
                Text("Zeit: ${lunchidea.bestellzeit}", style = MaterialTheme.typography.body2)
                Text(
                    text = "Bezahlungsart: ${lunchidea.bezahlungsart}",
                    style = MaterialTheme.typography.body2
                )
                Divider()
                LazyColumn(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .size(300.dp)
                ) {
                    items(lunchidea.teilnehmer) { teilnehmer ->
                        Card(
                            modifier = Modifier
                                .padding(2.dp)
                                .fillMaxWidth(),
                            elevation = 3.dp,
                            backgroundColor = Color.LightGray
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Column(modifier = Modifier.padding(3.dp)) {
                                    Text(teilnehmer.getValue("Name"))
                                    Text(teilnehmer.getValue("Mahlzeit"))
                                }
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = "EditTeilnehmer",
                                    Modifier.clickable {
                                        onUpdateOrderClick(teilnehmer.get("Name").toString())
                                        Log.d(
                                            "Update Order",
                                            teilnehmer.get("Name").toString()
                                        )
                                    })

                            }

                        }

                    }

                }
                content()


            }

        }
    }
}
