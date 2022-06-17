package com.example.colunch.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.colunch.models.Lunchidea

class LunchideasModel: ViewModel() {
    private var lunchideas = mutableStateListOf<Lunchidea>()


    fun addLunchidea(lunchidea: Lunchidea){

            lunchideas.add(lunchidea)

    }

    fun getLunchideas(): List<Lunchidea>{
        return lunchideas
    }

    fun removeLunchidea(id: String){
        for(lunchidea in lunchideas){
            if(lunchidea.id == id){
                lunchideas.remove(lunchidea)
            }
        }
    }

    fun getLunchIdea(id: String): Lunchidea {
        return getLunchideas().filter { lunch -> lunch.id == id}[0]
    }

}