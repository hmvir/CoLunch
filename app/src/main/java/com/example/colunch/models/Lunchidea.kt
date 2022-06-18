package com.example.colunch.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlin.properties.Delegates

class Lunchidea{
    lateinit var appid: String
    lateinit var restaurant: String
    var bestellzeit by Delegates.notNull<Long>()
    lateinit var bezahlungsart: String
    lateinit var id: String
    var gesperrt: Boolean
    var orders = mutableStateListOf<Order>()


    constructor(
        _appid: String,
        _restaurant: String,
        _bestellzeit: Long,
        _bezahlungsart: String,
        _gesperrt: Boolean,
        _id: String,


    ){
        appid = _appid
        restaurant = _restaurant
        bestellzeit = _bestellzeit
        bezahlungsart = _bezahlungsart
        gesperrt = _gesperrt
        id = _id
    }

    fun addorder(order: Order){
        orders.add(order)
    }

    fun getorders(): MutableList<Order>{
        return orders
    }

    fun getorder(orderid: String): Order {
        return getorders().filter { order -> orderid == order.orderID}[0]
    }
}