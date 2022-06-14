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
fun RestaurantScreen(
    navController: NavController,
    RestaurantViewModel: Restaurantsmodel,
    restaurants: List<Restaurant>,
) {
    BottomTopBar("Restaurants", navController) {
        Column {
            SimpleButton(inputtext = "Add Restaurant")
            LazyColumn {
                items(restaurants) { restaurant ->
                    RestaurantRow(restaurant = restaurant)
                }
            }
        }
    }

}



