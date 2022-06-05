package com.example.colunch.models

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.colunch.viewmodels.LunchideasModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.jar.Attributes

fun addRestaurantToFirestore(beschreibung: String, name: String, website: String){
    val db = Firebase.firestore
    val restaurant = hashMapOf(
        "Beschreibung" to beschreibung,
        "Name" to name,
        "Website" to website
    )
    // Add a new document with a generated ID
    db.collection("Restaurant")
        .add(restaurant)
        .addOnSuccessListener { documentReference ->
            Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w("TAG", "Error adding document", e)
        }
}

fun getRestaurantsFromFirestore(viewmodel: LunchideasModel){
    val db = Firebase.firestore

    //Echtzeitupdates-Sammlung
    db.collection("Restaurant")
        //.whereEqualTo("state", "CA")
        .addSnapshotListener { value, e ->
            if (e != null) {
                Log.w("TAG", "Listen failed.", e)
                return@addSnapshotListener
            }


            for (doc in value!!) {
                Log.d("TAG", doc.toString())
                doc.data.let {
                    Log.d("FirestoreTest", it.getValue("Name").toString())

                }
            }
            Log.d("TAG", "Current cites in CA: ")
        }
}
