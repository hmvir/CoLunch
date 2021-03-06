package com.example.colunch.screens


import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.colunch.models.Lunchidea
import com.example.colunch.models.deleteOrderFromFirestore
import com.example.colunch.navigation.Screens
import com.example.colunch.viewmodels.LunchideasModel
import com.example.colunch.widgets.BottomTopBar
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DetailLunchScreen(
    db: FirebaseFirestore,
    navController: NavHostController,
    lunchId: String?,
    lunchViewModel: LunchideasModel,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    appid: String,
    onDeleteOrderClick: (String, String) -> Unit = { s: String, s1: String -> },
) {
    var lunchIdea = lunchId?.let { lunchViewModel.getLunchIdea(it) }
    if (lunchIdea != null) {
        val timeD1 = Date(lunchIdea.bestellzeit * 1000)
        val sdf1 = SimpleDateFormat("HH:mm")
        val Time1: String = sdf1.format(timeD1)
        BottomTopBar(

            title = lunchIdea.restaurant + ' ' + Time1,
            navController,
            scaffoldState,
            scope

        ) {

            Log.d("lunch detail", lunchId.toString())
            LunchDetails(navController,
                lunchIdea,
                db,
                scaffoldState,
                scope,
                appid,
                onUpdateOrderClick = { name, orderId -> navController.navigate(Screens.Orderscreen.name + "update?lunchId=$lunchId&orderId=$orderId&name=$name") },
                onDeleteOrderClick = { orderId -> onDeleteOrderClick(lunchIdea.id, orderId) }
            ) {

                if (lunchIdea.gesperrt != true) {
                    if(lunchIdea.bestellzeit > Timestamp.now().seconds ){
                        Button(modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),

                            onClick = {
                                navController.navigate(Screens.Orderscreen.name + "add?lunchId=$lunchId")

                            }) {
                            Text(text = "Add Order")
                        }
                    }

                }
            }


        }

    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LunchDetails(
    navController: NavController,
    lunchidea: Lunchidea,
    db: FirebaseFirestore,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    appid: String,
    onUpdateOrderClick: (String, String) -> Unit = { s: String, s1: String -> },
    onDeleteOrderClick: (String) -> Unit = {},
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
                val timeD = Date(lunchidea.bestellzeit * 1000)
                val sdf = SimpleDateFormat("HH:mm")
                val Time: String = sdf.format(timeD)

                Text("Zeit: ${Time}", style = MaterialTheme.typography.body2)
                Text(
                    text = "Bezahlungsart: ${lunchidea.bezahlungsart}",
                    style = MaterialTheme.typography.body2
                )
                Divider(modifier = Modifier.padding(4.dp))

                LazyColumn(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .size(300.dp)


                ) {
                    items(lunchidea.orders) { teilnehmer ->
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
                                    //Text(teilnehmer.getValue("Name"))
                                    //Text(teilnehmer.getValue("Mahlzeit"))
                                    Text(text = teilnehmer.name)
                                    Text(text = teilnehmer.mahlzeit)
                                }
                                Row(modifier = Modifier.padding(5.dp)) {

                                    if (!lunchidea.gesperrt ) {
                                        if(lunchidea.bestellzeit < Timestamp.now().seconds)
                                        else if (teilnehmer.appID == appid) {
                                            Icon(
                                                imageVector = Icons.Default.Edit,
                                                contentDescription = "EditOrder",
                                                Modifier.clickable {
                                                    onUpdateOrderClick(
                                                        teilnehmer.name,
                                                        teilnehmer.orderID
                                                    )
                                                    Log.d(
                                                        "Update Order",
                                                        teilnehmer.name
                                                    )
                                                }
                                            )
                                            Box(modifier = Modifier.padding(4.dp))
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "DeleteOrder",
                                                Modifier.clickable {
                                                    onDeleteOrderClick(teilnehmer.orderID)
                                                    Log.d(
                                                        "Delete Order",
                                                        teilnehmer.name
                                                    )
                                                })
                                        }
                                        else if (lunchidea.appid == appid){
                                            Log.d("IDs", "lunchidea.appid: " + lunchidea.appid )
                                            Log.d("IDs", "local.appid: $appid")
                                            Icon(
                                                imageVector = Icons.Default.Edit,
                                                contentDescription = "EditOrder",
                                                Modifier.clickable {
                                                    onUpdateOrderClick(
                                                        teilnehmer.name,
                                                        teilnehmer.orderID
                                                    )
                                                    Log.d(
                                                        "Update Order",
                                                        teilnehmer.name
                                                    )
                                                }
                                            )
                                            Box(modifier = Modifier.padding(4.dp))
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "DeleteOrder",
                                                Modifier.clickable {
                                                    onDeleteOrderClick(teilnehmer.orderID)
                                                    Log.d(
                                                        "Delete Order",
                                                        teilnehmer.name
                                                    )
                                                })
                                        }

                                    }

                                }


                            }

                        }

                    }

                }
                content()


            }

        }
    }
}
