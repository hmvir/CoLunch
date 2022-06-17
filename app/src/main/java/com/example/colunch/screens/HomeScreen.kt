package com.example.colunch.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.colunch.models.Lunchidea
import com.example.colunch.models.Restaurant
import com.example.colunch.models.lockunlocklunchideaInFirestore
import com.example.colunch.navigation.Screens
import com.example.colunch.viewmodels.LunchideasModel
import com.example.colunch.viewmodels.Restaurantsmodel
import com.example.colunch.widgets.*
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    RestaurantViewModel: Restaurantsmodel,
    restaurants: List<Restaurant>,
    LunchideaViewModel: LunchideasModel,
    lunchideas: List<Lunchidea>,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    onLockLunchscreenClick: (String, Boolean) -> Unit ={ b: String, s: Boolean -> },

) {
    BottomTopBar("Lunch Ideas",navController, scaffoldState, scope) {
        Column {
            //var inputtext = simpleTextField()
            //SimpleButton(inputtext)

            LazyColumn {/*
                        items(restaurants) { restaurant ->
                            Greeting(name = restaurant.beschreibung)

                        }*/
                items(lunchideas) { lunchidea ->
                    LunchideaRow(lunchidea = lunchidea,
                        navController = navController,
                        onLockLunchscreenClick = { lunchId, locked -> onLockLunchscreenClick(lunchId, locked) },
                    onOpenDetailLunchScreenClick = { lunchId ->
                        navController.navigate(route = Screens.DetailLunchscreen.name + "/$lunchId")

                    })
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun LunchideaRow(
    lunchidea: Lunchidea,
    navController: NavController,
    onOpenDetailLunchScreenClick: (String) -> Unit = {},
    onLockLunchscreenClick: (String, Boolean) -> Unit ={ b: String, s: Boolean -> },
    content: @Composable () -> Unit = {}
) {

    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable { onOpenDetailLunchScreenClick(lunchidea.id) },

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
                Column(modifier = Modifier.width(100.dp)) {
                    Text(text = lunchidea.restaurant, style = MaterialTheme.typography.h6)
                    Text("Zeit: ${lunchidea.bestellzeit}", style = MaterialTheme.typography.body2)
                }
            }
            Row(Modifier.padding(10.dp)) {
                if(lunchidea.gesperrt == true){
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "LockLunchidea",
                        Modifier.clickable {
                            onLockLunchscreenClick(lunchidea.id,false)
                        }
                    )
                }
                else{
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "LockLunchidea",
                    Modifier.clickable {
                        onLockLunchscreenClick(lunchidea.id,true)
                    }
                )
                Box(modifier = Modifier.padding(10.dp))
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "EditLunchidea",
                    Modifier.clickable {
                        navController.navigate(Screens.UpdateLunchscreen.name + "/update?lunchId=${lunchidea.id}")
                    }
                )
                }
            }


            content()
        }
    }
}



