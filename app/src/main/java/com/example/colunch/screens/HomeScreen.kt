package com.example.colunch.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
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
    lunchideas: List<Lunchidea>
) {
    BottomTopBar("Lunch Ideas",navController) {
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




