package com.example.colunch.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class LunchideasModel: ViewModel() {
    private var favorites = mutableStateListOf<String>()



    fun addRemoveFavorit(movie: String){
        if(movie !in favorites) {
            favorites.add(movie)
        }
        else {
            favorites.remove(movie)
        }
    }

    fun addFavorit(text: String){
        if(text !in favorites) {
            favorites.add(text)
        }
    }

    fun getFavorits(): List<String>{
        return favorites
    }


}