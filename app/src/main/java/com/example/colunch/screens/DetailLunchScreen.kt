package com.example.colunch.screens


import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.colunch.viewmodels.LunchideasModel
import com.example.colunch.widgets.BottomTopBar
import com.example.colunch.widgets.LunchDetails

@Composable
fun DetailLunchScreen(
    //lunchidea: Lunchidea,
    navController: NavHostController,
    lunchId:Long?,
    lunchViewModel: LunchideasModel
) {
    var lunchIdea = lunchId?.let { lunchViewModel.getLunchIdea(it) }
    if (lunchIdea != null) {
        BottomTopBar(title =  lunchIdea.restaurant  + ' ' + lunchIdea.bestellzeit, navController) {
                Log.d("lunch detail", lunchId.toString())
                LunchDetails(lunchIdea)

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
