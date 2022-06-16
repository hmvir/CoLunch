package com.example.colunch.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.colunch.models.*
import com.example.colunch.screens.*
import com.example.colunch.viewmodels.LunchideasModel
import com.example.colunch.viewmodels.Restaurantsmodel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.ktx.Firebase

@Composable
fun MyNavigation(){
    val navController = rememberNavController()
    val db = Firebase.firestore
    val lunchViewModel: LunchideasModel = viewModel()
    val restaurantViewModel: Restaurantsmodel = viewModel()
    getRestaurantChangesFromFirestore(db, restaurantViewModel)
    getLunchideasFromFirestore(db, lunchViewModel)

    FirebaseInstallations.getInstance().id.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            Log.d("Installations", "Installation ID: " + task.result)
        } else {
            Log.e("Installations", "Unable to get Installation ID")
        }
    }


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
        composable(Screens.DetailLunchscreen.name + "/{lunchId}",
            arguments = listOf(navArgument("lunchId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            DetailLunchScreen(
                db,
                navController = navController,
                lunchViewModel = lunchViewModel,
                lunchId = backStackEntry.arguments?.getString("lunchId"),

            )
        }
        composable(Screens.Restaurantsscreen.name) {
            RestaurantScreen(
                navController,
                restaurantViewModel,
                restaurantViewModel.getRestaurants(),
            )
        }
        composable(Screens.AddLunchscreen.name) {
            AddLunchScreen(navController,restaurantViewModel)
        }


    }

}