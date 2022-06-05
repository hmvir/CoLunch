package com.example.colunch.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.colunch.models.getRestaurantsFromFirestore
import com.example.colunch.screens.HomeScreen
import com.example.colunch.viewmodels.LunchideasModel

@Composable
fun MyNavigation(){
    val navController = rememberNavController()
    val lunchViewModel: LunchideasModel = viewModel()
    //addToRealtimeDatabase()
    //addToFirestore()
    getRestaurantsFromFirestore(lunchViewModel)
    //getFromRealTimeDatabase(favoritViewModel)
    NavHost(navController  = navController,
        startDestination = Screens.Homescreen.name
    ){
        composable(Screens.Homescreen.name) { HomeScreen(navController, lunchViewModel,lunchViewModel.getFavorits()) }

    }

}