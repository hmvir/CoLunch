package com.example.colunch.models

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Restaurant(
    val name: String,
    val beschreibung: String,
    val website: String)
{

    fun getRestaurant(): Restaurant {
        return this
    }


}