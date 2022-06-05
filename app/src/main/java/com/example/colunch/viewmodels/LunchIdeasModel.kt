package com.example.colunch.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class LunchideasModel: ViewModel() {
    private var lunchideas = mutableStateListOf<String>()



    fun addRemoveFavorit(text: String){
        if(text !in lunchideas) {
            lunchideas.add(text)
        }
        else {
            lunchideas.remove(text)
        }
    }

    fun addFavorit(text: String){
        if(text !in lunchideas) {
            lunchideas.add(text)
        }
    }

    fun getFavorits(): List<String>{
        return lunchideas
    }


}