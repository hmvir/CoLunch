package com.example.colunch.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.colunch.models.Restaurant

class Restaurantsmodel: ViewModel() {
    private var restaurants = mutableStateListOf<Restaurant>()


    fun addRemoveRestaurants(restaurant: Restaurant){
        if(restaurant !in restaurants) {
            restaurants.add(restaurant)
        }
        else {
            restaurants.remove(restaurant)
        }
    }

    fun AddRestaurant(restaurant: Restaurant){
        if(restaurant !in restaurants) {
            restaurants.add(restaurant)
        }
    }

    fun getRestaraunts(): List<Restaurant>{
        return restaurants
    }


}