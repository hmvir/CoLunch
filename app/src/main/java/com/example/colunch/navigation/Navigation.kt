package com.example.colunch.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.colunch.models.*
import com.example.colunch.screens.HomeScreen
import com.example.colunch.viewmodels.LunchideasModel
import com.example.colunch.viewmodels.Restaurantsmodel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun MyNavigation(){
    val navController = rememberNavController()
    val db = Firebase.firestore
    val lunchViewModel: LunchideasModel = viewModel()
    val restaurantViewModel: Restaurantsmodel = viewModel()
    getRestaurantChangesFromFirestore(db, restaurantViewModel)
    getLunchideasFromFirestore(db, lunchViewModel, restaurantViewModel)

    //addLunchideaToFirestore(db,"test",false,"tetst","test","test","test")
    //getIdLunchideaToFirebase(db,"tetst","test1","test1")
    //addTeilnehmerLunchideaToFirebase(db,"5YOtTBcbWvwsE4CBKdnF","test3","test3")

    NavHost(navController  = navController,
        startDestination = Screens.Homescreen.name
    ){
        composable(Screens.Homescreen.name) {
            HomeScreen(
                navController,
                restaurantViewModel,
                restaurantViewModel.getRestaurants(),
                lunchViewModel,
                lunchViewModel.getLunchideas()
            )
        }

    }

}