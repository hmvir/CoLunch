package com.example.colunch.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.colunch.models.Restaurant

class Restaurantsmodel: ViewModel() {
    private var restaurants = mutableStateListOf<Restaurant>()


    fun addRestaurant(restaurant: Restaurant){
        if(restaurant !in restaurants) {
            restaurants.add(restaurant)
        }
    }

    fun removeRestaurantByName(name: String){
        for(restaurant in restaurants){
            if(restaurant.name == name){
                restaurants.remove(restaurant)
            }
        }
    }

    fun getRestaurants(): List<Restaurant>{
        return restaurants
    }
/*
    fun getRestaurant(name: String): Restaurant {
        val r = restaurants.find { restaurant -> restaurant.name == name }
        /*
        val r = Restaurant
        for(restaurant in restaurants){
            if(restaurant.name == name){
                r = restaurant
            }
        }*/
        return r
    }

 */

    fun modifyRestaurant(name: String, beschreibung: String, website: String){
        for(restaurant in restaurants){
            if(restaurant.name == name){
                restaurant.beschreibung = beschreibung
                restaurant.website = website
            }
            println(restaurant.beschreibung)
        }
    }

}