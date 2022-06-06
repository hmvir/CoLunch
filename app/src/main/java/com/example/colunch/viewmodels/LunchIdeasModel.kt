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

    fun removeLunchidea(bestellzeit: String){
        for(lunchidea in lunchideas){
            if(lunchidea.bestellzeit == bestellzeit){
                lunchideas.remove(lunchidea)
            }
        }
    }

}