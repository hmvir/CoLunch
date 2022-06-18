package com.example.colunch.navigation

import android.util.Log
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch

@Composable
fun MyNavigation(scaffoldState: ScaffoldState, appid: String) {
    val navController = rememberNavController()
    val db = Firebase.firestore
    val lunchViewModel: LunchideasModel = viewModel()
    val restaurantViewModel: Restaurantsmodel = viewModel()
    val scope = rememberCoroutineScope()

    getRestaurantChangesFromFirestore(db, restaurantViewModel)
    getLunchideasFromFirestore(db, lunchViewModel)

    FirebaseInstallations.getInstance().id.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            Log.d("Installations", "Installation ID: " + task.result)
            var installationID = task.result.toString()
        } else {
            Log.e("Installations", "Unable to get Installation ID")
        }
    }


    //getTeilnehmerFromFirestore(db,lunchViewModel)

    /*
    for(element in lunchViewModel.getLunchideas()){
        Log.d("TAG", element.teilnehmer.toString())
    }



 */
    //changeTeilnehmerLunchideaToFirebase(db,"I8Rr90LTS5GDM9TJ2snz","Hami","test")
    //addLunchideaToFirestore(db,"test",false,"tetst","test","test","test","mahlzeit")
    //getIdLunchideaToFirebase(db,"tetst","test1","test1")
    //addTeilnehmerLunchideaToFirebase(db,"5YOtTBcbWvwsE4CBKdnF","test3","test3")

    NavHost(
        navController = navController,
        startDestination = Screens.Splashscreen.name) {
        composable(Screens.Splashscreen.name) {
            SplashScreen(navController)
        }
        composable(Screens.Homescreen.name) {
            HomeScreen(
                navController,
                restaurantViewModel,
                restaurantViewModel.getRestaurants(),
                lunchViewModel,
                lunchViewModel.getLunchideas(),
                scaffoldState,
                scope,
                appid
            ){ lunchId, locked ->
                lockunlocklunchideaInFirestore(db, lunchId, locked)
            }

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
                scaffoldState = scaffoldState,
                scope = scope,
                appid = appid,
                ){ lunchId, oderId ->
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar("deleted Order successfully")
                }
                deleteOrderFromFirestore(db,lunchId,oderId)
            }
        }
        composable(Screens.Restaurantsscreen.name) {
            RestaurantScreen(
                navController,
                restaurantViewModel,
                restaurantViewModel.getRestaurants(),
                scaffoldState,
                scope
            )
        }

        composable(Screens.AddLunchscreen.name) {
            AddLunchScreen(navController,
                restaurantViewModel,
                scaffoldState = scaffoldState,
                scope = scope,
            appid){ lunchidealist, timestamp ->
                Log.d("AddLunch", lunchidealist.toString())
                addLunchideaToFirestore(
                    db = db,
                    appid = appid,
                    name = lunchidealist[0],
                    restaurant = lunchidealist[1],
                    bestellzeit = timestamp,
                    bezahlungsart =lunchidealist [2],
                    mahlzeit = lunchidealist[3],
                gesperrt = false)
                navController.popBackStack()
            }
        }

        composable(Screens.UpdateLunchscreen.name + "/update?lunchId={lunchId}",
            arguments = listOf(navArgument("lunchId") {
                type = NavType.StringType
            })
            ){ backStackEntry ->
            var lunchId = backStackEntry.arguments?.getString("lunchId").toString()
            UpdateLunchScreen(
                navController = navController,
                lunchideasModel= lunchViewModel,
                lunchId = lunchId,
                scaffoldState = scaffoldState,
                scope = scope
            ){ time ->
                updatelunchideaInFirestore(db,lunchId,time)
                navController.popBackStack()
            }
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
            AddUpdateOrderScreen(
                navController = navController,
                name,
                "Add",
                scaffoldState,
                scope
            ) { orderlist ->
                Log.d("OrderScreen", "Backstack Add")
                addOrderToFirestore(db, lunchId, orderlist[0], orderlist[1], appid)
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar("added Order successfully")
                }
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
            AddUpdateOrderScreen(navController = navController, name = name, "Update",scaffoldState, scope){ orderlist ->
                var mahlzeit = orderlist[1]
                Log.d("OrderScreen", "Backstack Update")
                updateorderInFirestore(db,lunchId,orderId,mahlzeit)
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar("updated Order successfully")
                }
                navController.popBackStack()
            }

        }




    }

}