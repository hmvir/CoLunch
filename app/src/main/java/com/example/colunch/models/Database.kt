package com.example.colunch.models


import android.content.ContentValues.TAG
import android.util.Log
import com.example.colunch.viewmodels.LunchideasModel
import com.example.colunch.viewmodels.Restaurantsmodel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

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

fun getLunchideasFromFirestore(
    db: FirebaseFirestore,
    lunchideasmodel: LunchideasModel,
){
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
                        val  bestellzeit = dc.document.data.getValue("Bestellzeit") as Timestamp
                        //val map = dc.document.data.getValue("Teilnehmer") as MutableList<*>
                        val lunchidea = Lunchidea(
                                dc.document.data.getValue("AppID").toString(),
                                restaurant,
                                bestellzeit.seconds,
                                dc.document.data.getValue("Bezahlungsart").toString(),
                                gesperrt as Boolean,
                                dc.document.id,
                                //mutableStateListOf()
                                //map as MutableList<MutableMap<String, String>>
                            )
                        /*
                        val timestamp = dc.document.data.getValue("timestamp") as Timestamp

                        val timeD = Date(timestamp.seconds * 1000)
                        val sdf = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss")
                        val Time: String = sdf.format(timeD)
                        Log.d("timestamp", Time)
                        */



                        lunchideasmodel.addLunchidea(lunchidea)
                        getTeilnehmerFromFirestore(db,dc.document.id, lunchideasmodel)
                    }
                    DocumentChange.Type.MODIFIED -> {
                        Log.d(TAG, "Modified Lunchidea: ${dc.document.data}")
                        var lunchidea = lunchideasmodel.getLunchIdea(dc.document.id)
                        val  bestellzeit = dc.document.data.getValue("Bestellzeit") as Timestamp
                        //val map = dc.document.data.getValue("Teilnehmer") as MutableList<*>
                        lunchidea.gesperrt = dc.document.data.getValue("gesperrt") as Boolean
                        lunchidea.bestellzeit = bestellzeit.seconds
                        lunchidea.bezahlungsart = dc.document.data.getValue("Bezahlungsart").toString()
                        //lunchidea.teilnehmer = map as MutableList<MutableMap<String, String>>

                    }
                    DocumentChange.Type.REMOVED -> {
                        Log.d(TAG, "Removed Lunchidea: ${dc.document.data}")
                        var lunchidea = lunchideasmodel.getLunchIdea(dc.document.id)
                        lunchideasmodel.removeLunchidea(lunchidea)
                    }
                }
            }
        }
}

fun getTeilnehmerFromFirestore(
    db: FirebaseFirestore,
    lunchideaID: String,
    lunchideasmodel: LunchideasModel,
){

        db.collection("Lunchidea").document(lunchideaID).collection("Teilnehmer")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w("TAG", "listen:error", e)
                    return@addSnapshotListener
                }

                for (dc in snapshots!!.documentChanges) {
                    when (dc.type) {
                        DocumentChange.Type.ADDED -> {
                            Log.d(TAG, "Added Order: ${dc.document.data}")
                            var lunchidea = lunchideasmodel.getLunchIdea(lunchideaID)

                            var order = Order(dc.document.id,dc.document.data.get("Name").toString(),dc.document.data.get("Mahlzeit").toString(),dc.document.data.get("AppID").toString())

                            Log.d("TAG",order.orderID)
                            lunchidea.addorder(order)



                        }
                        DocumentChange.Type.MODIFIED -> {
                            Log.d(TAG, "Modified Order: ${dc.document.data}")
                            var lunchidea = lunchideasmodel.getLunchIdea(lunchideaID)
                            var deleteorder = lunchidea.getorder(dc.document.id)
                            lunchidea.orders.remove(deleteorder)
                            var addorder = Order(dc.document.id,dc.document.data.get("Name").toString(),dc.document.data.get("Mahlzeit").toString(),dc.document.data.get("AppID").toString())

                            lunchidea.addorder(addorder)


                        }
                        DocumentChange.Type.REMOVED -> {
                            Log.d(TAG, "Removed Order: ${dc.document.data}")
                            var lunchidea = lunchideasmodel.getLunchIdea(lunchideaID)

                            var order = lunchidea.getorder(dc.document.id)
                            lunchidea.orders.remove(order)

                        }
                    }
                }
            }

}




fun addLunchideaToFirestore(
    db: FirebaseFirestore,
    appid: String,
    restaurant: String,
    gesperrt: Boolean,
    bestellzeit: Timestamp,
    bezahlungsart: String,
    name: String,
    mahlzeit: String,
    ){
    val lunchidea = hashMapOf(
        "Restaurant" to restaurant,
        "AppID" to appid,
        "Bezahlungsart" to bezahlungsart,
        "Bestellzeit" to bestellzeit,
        "gesperrt" to gesperrt,
        )

    // Add a new document with a generated ID
    db.collection("Lunchidea")
        .add(lunchidea)
        .addOnSuccessListener { documentReference ->
            Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
            addOrderToFirestore(db,documentReference.id,name,mahlzeit,appid)
        }
        .addOnFailureListener { e ->
            Log.w("TAG", "Error adding document", e)
        }

    //db.collection("Lunchidea").document(documentReference.id).collection("Teilnehmer").add("test")

}

fun addOrderToFirestore(
    db: FirebaseFirestore,
    id: String,
    name: String,
    mahlzeit: String,
    appid: String
){
    db.collection("Lunchidea").document(id).collection("Teilnehmer")
        .add(hashMapOf(
            "Name" to name,
            "Mahlzeit" to mahlzeit,
            "AppID" to appid
            //"Teilnehmer" to arrayListOf<Map<String,String>>(mutableMapOf("Name" to name, "Mahlzeit" to mahlzeit)),

        ))
}

fun deleteOrderFromFirestore(db: FirebaseFirestore,documentId: String, orderid: String){
    db.collection("Lunchidea").document(documentId).collection("Teilnehmer").document(orderid)
        .delete()
}

fun updateorderInFirestore(db: FirebaseFirestore, documentId: String, orderid: String, mahlzeit: String){
    db.collection("Lunchidea").document(documentId).collection("Teilnehmer").document(orderid)
        .update("Mahlzeit", mahlzeit)
}

fun updatelunchideaInFirestore(db: FirebaseFirestore, documentId: String, time: Timestamp){
    db.collection("Lunchidea").document(documentId)
        .update("Bestellzeit", time)
}

fun lockunlocklunchideaInFirestore(db: FirebaseFirestore,documentId: String, locked: Boolean){
    db.collection("Lunchidea").document(documentId)
        .update("gesperrt", locked)
}






