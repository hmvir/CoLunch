package com.example.colunch.models

import android.content.ContentValues
import android.util.Log
import com.example.colunch.viewmodels.LunchideasModel
import com.example.colunch.viewmodels.Restaurantsmodel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

fun getRestaurantsFromFirestore(restaurantsmodel: Restaurantsmodel){
    val db = Firebase.firestore

    db.collection("Restaurant")
        .get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                val r = Restaurant(document.data.getValue("Name").toString(),document.data.getValue("Beschreibung").toString(),document.data.getValue("Website").toString())
                restaurantsmodel.addRestaurant(r)
            }
        }
        .addOnFailureListener { exception ->
            Log.w(ContentValues.TAG, "Error getting documents: ", exception)
        }
    //Echtzeitupdates-Sammlung
    /*
    db.collection("Restaurant")
        //.whereEqualTo("state", "CA")
        .addSnapshotListener { value, e ->
            if (e != null) {
                Log.w("TAG", "Listen failed.", e)
                return@addSnapshotListener
            }


            for (doc in value!!) {
                //Log.d("TAG", doc.toString())
                doc.data.let {
                    //Log.d("FirestoreTest", it.getValue("Name").toString())
                    val r = Restaurant(it.getValue("Name").toString(),it.getValue("Beschreibung").toString(),it.getValue("Website").toString())
                    restaurantsmodel.addRestaurant(r)
                }
            }
        }

     */
}

//TestAdd
fun addToFirestore(inputtext: String) {
    // Create a new user with a first and last name
    val db = Firebase.firestore
    val user = hashMapOf(
        "first" to inputtext,
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

fun getFromFirestore(lunchideaviewmodel: LunchideasModel){
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

            val array = ArrayList<String>()
            for (doc in value!!) {
                Log.d("TAG", doc.toString())
                doc.data.let {
                    array.add(it.toString())
                    //lunchideaviewmodel.addLunchidea(it.toString())
                }
            }
            Log.d("TAG", "Current cites in CA: $array")
        }
}

fun getFromRealTimeDatabase(lunchideaviewmodel: LunchideasModel){
    val database = Firebase.database
    val myTest = database.getReference("Nachrichten")
    myTest.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val players: HashMap<String?, Any?> = HashMap()
            for (childSnapshot in snapshot.children) {
                players[childSnapshot.key] = childSnapshot.value

              //  lunchideaviewmodel.addLunchidea(childSnapshot.value.toString())
            }
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

fun changeTeilnehmerLunchideaToFirebase(db: FirebaseFirestore,
                                        id: String,
                                        name: String,
                                        mahlzeit: String
){


    val ref = db.collection("Lunchidea").document(id)
    ref.update("Teilnehmer", FieldValue.arrayRemove(mutableMapOf("Name" to name, "Mahlzeit" to mahlzeit)))

    /*
.set(teilnehmer, SetOptions.merge())
.addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
.addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

     */

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