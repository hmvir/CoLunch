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

fun getFromRealTimeDatabase(viewmodel: LunchideasModel){
    val database = Firebase.database
    val myTest = database.getReference("Nachrichten")
    myTest.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val players: HashMap<String?, Any?> = HashMap()
            for (childSnapshot in snapshot.children) {
                players[childSnapshot.key] = childSnapshot.value
                viewmodel.addFavorit(childSnapshot.value.toString())
            }
            Log.d("players", snapshot.value.toString())
            /*
            val output = snapshot.value as String
            favorites.add(output)
            */
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }
    })
}

fun addToRealtimeDatabase(){
    val database = Firebase.database
    val myRef = database.getReference("Nachrichten")
    //myRef.child("n1").push().setValue("Hallo Benjamin")
}

fun addToFirestore(){
    // Create a new user with a first and last name
    val db = Firebase.firestore
    val user = hashMapOf(
        "first" to "Ada",
        "last" to "Lovelace",
        "born" to 1815
    )
    // Add a new document with a generated ID
    db.collection("users")
        .add(user)
        .addOnSuccessListener { documentReference ->
            Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w("TAG", "Error adding document", e)
        }
}

fun getFromFirestore(viewmodel: LunchideasModel){
    val db = Firebase.firestore

/*


        //Einfache Abfrage
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("TAG", "${document.id} => ${document.data}")
                    favorites.add(document.data.toString())

                }
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
            }

        //Echtzeitupdates-Document
        val docRef = db.collection("users").document("Name")
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("TAG", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                Log.d("TAG", "Current data: ${snapshot.data}")
                favorites.add(snapshot.data.toString())
            } else {
                Log.d("TAG", "Current data: null")
            }
        }*/
    //Echtzeitupdates-Sammlung
    db.collection("users")
        //.whereEqualTo("state", "CA")
        .addSnapshotListener { value, e ->
            if (e != null) {
                Log.w("TAG", "Listen failed.", e)
                return@addSnapshotListener
            }

            val cities = ArrayList<String>()
            for (doc in value!!) {
                Log.d("TAG", doc.toString())
                doc.data.let {
                    cities.add(it.toString())
                    viewmodel.addFavorit(it.toString())
                }
            }
            Log.d("TAG", "Current cites in CA: $cities")
        }
}