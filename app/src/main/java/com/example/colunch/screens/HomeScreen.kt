package com.example.colunch.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.colunch.models.Lunchidea
import com.example.colunch.models.Restaurant
import com.example.colunch.navigation.Screens
import com.example.colunch.viewmodels.LunchideasModel
import com.example.colunch.viewmodels.Restaurantsmodel
import com.example.colunch.widgets.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    RestaurantViewModel: Restaurantsmodel,
    restaurants: List<Restaurant>,
    LunchideaViewModel: LunchideasModel,
    lunchideas: List<Lunchidea>,
    scaffoldState: ScaffoldState
) {
    BottomTopBar("Lunch Ideas",navController, scaffoldState) {
        Column {
            //var inputtext = simpleTextField()
            //SimpleButton(inputtext)
            LazyColumn {/*
                        items(restaurants) { restaurant ->
                            Greeting(name = restaurant.beschreibung)

                        }*/
                items(lunchideas) { lunchidea ->
                    LunchideaRow(lunchidea = lunchidea,
                    onItemClick = { lunchId ->
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



