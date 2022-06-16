package com.example.colunch.models

import android.content.ContentValues.TAG
import android.util.Log
import com.example.colunch.viewmodels.LunchideasModel
import com.example.colunch.viewmodels.Restaurantsmodel
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

fun addRestaurantToFirestore(db: FirebaseFirestore, beschreibung: String, name: String, website: String){
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



fun getRestaurantChangesFromFirestore(db: FirebaseFirestore, restaurantsmodel: Restaurantsmodel){
     db.collection("Restaurant")
        .addSnapshotListener { snapshots, e ->
            if (e != null) {
                Log.w("TAG", "listen:error", e)
                return@addSnapshotListener
            }

            for (dc in snapshots!!.documentChanges) {
                when (dc.type) {
                    DocumentChange.Type.ADDED -> {
                        Log.d(TAG, "Added Restaurant: ${dc.document.data}")
                        restaurantsmodel.addRestaurant(Restaurant(dc.document.data.getValue("Name").toString(),dc.document.data.getValue("Beschreibung").toString(),dc.document.data.getValue("Website").toString()))
                    }
                    DocumentChange.Type.MODIFIED -> {
                        Log.d(TAG, "Modified Restaurant: ${dc.document.data}")
                        restaurantsmodel.modifyRestaurant(dc.document.data.getValue("Name").toString(),dc.document.data.getValue("Beschreibung").toString(),dc.document.data.getValue("Website").toString())
                    }
                    DocumentChange.Type.REMOVED -> {
                        Log.d(TAG, "Removed Restaurant: ${dc.document.data}")
                        restaurantsmodel.removeRestaurantByName(
                            dc.document.data.getValue("Name").toString()
                        )
                    }
                }
            }
        }
}

fun getLunchideasFromFirestore(db: FirebaseFirestore, lunchideasmodel: LunchideasModel){
    db.collection("Lunchidea")
        .addSnapshotListener { snapshots, e ->
            if (e != null) {
                Log.w("TAG", "listen:error", e)
                return@addSnapshotListener
            }

            for (dc in snapshots!!.documentChanges) {
                when (dc.type) {
                    DocumentChange.Type.ADDED -> {
                        Log.d(TAG, "Added Lunchidea: ${dc.document.data}")
                        val restaurant = dc.document.data.getValue("Restaurant").toString()
                        val gesperrt = dc.document.data.getValue("gesperrt")
                        val map = dc.document.data.getValue("Teilnehmer") as MutableList<*>
                        val lunchidea = Lunchidea(
                                restaurant,
                                dc.document.data.getValue("Bestellzeit").toString(),
                                dc.document.data.getValue("Bezahlungsart").toString(),
                                gesperrt as Boolean,
                                dc.document.id,
                            map as MutableList<MutableMap<String, String>>
                            )
                        lunchideasmodel.addLunchidea(lunchidea)
                    }
                    DocumentChange.Type.MODIFIED -> {
                        Log.d(TAG, "Modified Lunchidea: ${dc.document.data}")
                        var lunchidea = lunchideasmodel.getLunchIdea(dc.document.id)
                        val map = dc.document.data.getValue("Teilnehmer") as MutableList<*>
                        lunchidea.bestellzeit = dc.document.data.getValue("Bestellzeit").toString()
                        lunchidea.bezahlungsart = dc.document.data.getValue("Bezahlungsart").toString()
                        lunchidea.teilnehmer = map as MutableList<MutableMap<String, String>>

                    }
                    DocumentChange.Type.REMOVED -> {
                        Log.d(TAG, "Removed Lunchidea: ${dc.document.data}")
                        lunchideasmodel.removeLunchidea(
                            dc.document.data.getValue("ID").toString()
                        )

                    }
                }
            }
        }
}

fun addLunchideaToFirestore(
        db: FirebaseFirestore,
        restaurant: String,
        gesperrt: Boolean,
        id: Int,
        bestellzeit: String,
        bezahlungsart: String,
        name: String,
        mahlzeit: String,
    ){
    val lunchidea = hashMapOf(
        "Restaurant" to restaurant,
        "Bezahlungsart" to bezahlungsart,
        "Bestellzeit" to bestellzeit,
        "gesperrt" to gesperrt,
        "ID" to id,
        "Teilnehmer" to arrayListOf<Map<String,String>>(mutableMapOf("Name" to name, "Mahlzeit" to mahlzeit)),

    )
    // Add a new document with a generated ID
    db.collection("Lunchidea")
        .add(lunchidea)
        .addOnSuccessListener { documentReference ->
            Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w("TAG", "Error adding document", e)
        }
}

fun getIdLunchideaToFirebase(
    db: FirebaseFirestore,
    bestellzeit:String,
    name: String,
    mahlzeit: String
    ){
    db.collection("Lunchidea")
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                Log.d("TAG", "${document.id} => ${document.data}")
                if(document.data.getValue("Bestellzeit").toString() == bestellzeit){
                    addTeilnehmerLunchideaToFirebase(db,document.id, name, mahlzeit)
                }
            }
        }
        .addOnFailureListener { exception ->
            Log.w("TAG", "Error getting documents.", exception)
        }
}

fun addTeilnehmerLunchideaToFirebase(db: FirebaseFirestore,
                                     id: String,
                                     name: String,
                                     mahlzeit: String
                                     ){


    val ref = db.collection("Lunchidea").document(id)
    ref.update("Teilnehmer", FieldValue.arrayUnion(mutableMapOf("Name" to name, "Mahlzeit" to mahlzeit)))
            /*
        .set(teilnehmer, SetOptions.merge())
        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
        .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

             */

}



