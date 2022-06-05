package com.example.colunch.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.colunch.models.Restaurant

class Restaurantsmodel: ViewModel() {
    private var restaurants = mutableStateListOf<Restaurant>()


    fun addRemoveFavorit(restaurant: Restaurant){
        if(restaurant !in restaurants) {
            restaurants.add(restaurant)
        }
        else {
            restaurants.remove(restaurant)
        }
    }

    fun addFavorit(restaurant: Restaurant){
        if(restaurant !in restaurants) {
            restaurants.add(restaurant)
        }
    }

    fun getFavorits(): List<Restaurant>{
        return restaurants
    }


}