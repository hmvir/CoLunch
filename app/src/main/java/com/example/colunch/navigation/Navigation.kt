package com.example.colunch.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.colunch.models.getFromFirestore
import com.example.colunch.screens.HomeScreen
import com.example.colunch.viewmodels.LunchideasModel

@Composable
fun MyNavigation(){
    val navController = rememberNavController()
    val favoritViewModel: LunchideasModel = viewModel()
    //addToRealtimeDatabase()
    //addToFirestore()
    getFromFirestore(favoritViewModel)
    //getFromRealTimeDatabase(favoritViewModel)
    NavHost(navController  = navController,
        startDestination = Screens.Homescreen.name
    ){
        composable(Screens.Homescreen.name) { HomeScreen(navController, favoritViewModel,favoritViewModel.getFavorits()) }

    }

}