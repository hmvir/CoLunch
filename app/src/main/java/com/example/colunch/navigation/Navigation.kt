package com.example.colunch.navigation

import android.util.Log
import androidx.compose.material.ScaffoldState
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
import com.google.firebase.ktx.Firebase

@Composable
fun MyNavigation(scaffoldState: ScaffoldState) {
    val navController = rememberNavController()
    val db = Firebase.firestore
    val lunchViewModel: LunchideasModel = viewModel()
    val restaurantViewModel: Restaurantsmodel = viewModel()
    getRestaurantChangesFromFirestore(db, restaurantViewModel)
    getLunchideasFromFirestore(db, lunchViewModel)
    //getTeilnehmerFromFirestore(db,lunchViewModel)

    /*
    for(element in lunchViewModel.getLunchideas()){
        Log.d("TAG", element.teilnehmer.toString())
    }

    FirebaseInstallations.getInstance().id.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            Log.d("Installations", "Installation ID: " + task.result)
        } else {
            Log.e("Installations", "Unable to get Installation ID")
        }
    }

 */
    //changeTeilnehmerLunchideaToFirebase(db,"I8Rr90LTS5GDM9TJ2snz","Hami","test")
    //addLunchideaToFirestore(db,"test",false,"tetst","test","test","test","mahlzeit")
    //getIdLunchideaToFirebase(db,"tetst","test1","test1")
    //addTeilnehmerLunchideaToFirebase(db,"5YOtTBcbWvwsE4CBKdnF","test3","test3")

    NavHost(
        navController = navController,
        startDestination = Screens.Homescreen.name
    ) {
        composable(Screens.Homescreen.name) {
            HomeScreen(
                navController,
                restaurantViewModel,
                restaurantViewModel.getRestaurants(),
                lunchViewModel,
                lunchViewModel.getLunchideas(),
                scaffoldState
            )
        }
        composable(
            Screens.DetailLunchscreen.name + "/{lunchId}",
            arguments = listOf(navArgument("lunchId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            DetailLunchScreen(
                db,
                navController = navController,
                lunchViewModel = lunchViewModel,
                lunchId = backStackEntry.arguments?.getString("lunchId"),
                scaffoldState = scaffoldState

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
            AddLunchScreen(navController, restaurantViewModel)
        }

        composable(
            Screens.Orderscreen.name + "add?lunchId={lunchId}&name={name}",
            arguments = listOf(
                navArgument("lunchId") { type = NavType.StringType },
                navArgument("name") {
                    defaultValue = ""
                    nullable = true
                    type = NavType.StringType
                },
            )
        ) { backStackEntry ->
            var lunchId = backStackEntry.arguments?.getString("lunchId").toString()

            var name = backStackEntry.arguments?.getString("name").toString()
            OrderScreen(
                navController = navController, name,"Add",scaffoldState
            ) { orderlist ->
                Log.d("OrderScreen", "Backstack Add")
                addOrderToFirestore(db, lunchId, orderlist[0], orderlist[1])
                navController.popBackStack()
            }

            /*   addTeilnehmerLunchideaToFirebase(db,lunchidea.id,orderlist[0],orderlist[1])
               navController.popBackStack()*/
        }
        composable(Screens.Orderscreen.name + "update?lunchId={lunchId}&orderId={orderId}&name={name}",
            arguments = listOf(
                navArgument("lunchId") { type = NavType.StringType },
                navArgument("orderId") { type = NavType.StringType },
                navArgument("name") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            var lunchId = backStackEntry.arguments?.getString("lunchId").toString()
            var orderId = backStackEntry.arguments?.getString("orderId").toString()
            var name = backStackEntry.arguments?.getString("name").toString()
            OrderScreen(navController = navController, name = name, "Update",scaffoldState){ orderlist ->
                var mahlzeit = orderlist[1]
                Log.d("OrderScreen", "Backstack Update")
                updateorderInFirestore(db,lunchId,orderId,mahlzeit)
                navController.popBackStack()
            }

        }
    }

}