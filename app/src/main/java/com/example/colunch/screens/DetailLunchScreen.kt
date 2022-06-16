package com.example.colunch.screens


import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.colunch.models.addTeilnehmerLunchideaToFirebase
import com.example.colunch.viewmodels.LunchideasModel
import com.example.colunch.widgets.BottomTopBar
import com.example.colunch.widgets.LunchDetails
import com.example.colunch.widgets.Order
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun DetailLunchScreen(
    //lunchidea: Lunchidea,
    db: FirebaseFirestore,
    navController: NavHostController,
    lunchId:String?,
    lunchViewModel: LunchideasModel
) {
    var lunchIdea = lunchId?.let { lunchViewModel.getLunchIdea(it) }
    if (lunchIdea != null) {
        BottomTopBar(title =  lunchIdea.restaurant  + ' ' + lunchIdea.bestellzeit, navController) {
                Log.d("lunch detail", lunchId.toString())
                LunchDetails(navController,lunchIdea, db){
                    Order(navController,db, lunchIdea.id){list->
                        addTeilnehmerLunchideaToFirebase(db,lunchId.toString(),list[0],list[1])
                    }
                }


        }

    }





}

/*
fun LunchIdeaDescription(

    lunchidea: Lunchidea
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        color = MaterialTheme.colors.background
    ) {
        Scaffold(topBar = {
            TopAppBar(
                title = { Text("Detailansicht") }
            )
            Row {
                Text(text = "Text") // Beschreibung der Lunchidee
                var order = simpleTextField()

            }
        }){

        }
    }

*/
