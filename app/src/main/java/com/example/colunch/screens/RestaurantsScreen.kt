package com.example.colunch.screens


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.example.colunch.models.Restaurant
import com.example.colunch.viewmodels.Restaurantsmodel
import com.example.colunch.widgets.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RestaurantScreen(
    navController: NavController,
    RestaurantViewModel: Restaurantsmodel,
    restaurants: List<Restaurant>,
) {
    BottomTopBar("Restaurants", navController, scaffoldState = rememberScaffoldState()) {
        Column {
           /* SimpleButton(
                inputtext = "Add Restaurant",
                transaction = "order",
                arrayListOf = arrayListOf<String>(buyer, order),
                db = db,
                id = id
            )*/
            LazyColumn {
                items(restaurants) { restaurant ->
                    RestaurantRow(restaurant = restaurant)
                }
            }
        }
    }

}



