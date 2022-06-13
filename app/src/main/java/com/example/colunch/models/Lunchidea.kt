package com.example.colunch.models

import kotlin.properties.Delegates

class Lunchidea{
    lateinit var restaurant: String
    lateinit var bestellzeit: String
    lateinit var bezahlungsart: String
    var id: Int = 0
    var gesperrt: Boolean
    lateinit var teilnehmer: MutableList<MutableMap<String,String>>

    constructor(
        _restaurant: String,
        _bestellzeit: String,
        _bezahlungsart: String,
        _gesperrt: Boolean,
        _id: Int
    ){
        restaurant = _restaurant
        bestellzeit = _bestellzeit
        bezahlungsart = _bezahlungsart
        gesperrt = _gesperrt
        id = _id
    }


    constructor(
        _restaurant: String,
        _bestellzeit: String,
        _bezahlungsart: String,
        _gesperrt: Boolean,
        _id: Int,
        _teilnemer: MutableList<MutableMap<String,String>>

    ){
        restaurant = _restaurant
        bestellzeit = _bestellzeit
        bezahlungsart = _bezahlungsart
        gesperrt = _gesperrt
        id = _id
        teilnehmer = _teilnemer
    }

    fun addTeilnehmer(mutableMap: MutableMap<String,String>){
        teilnehmer.add(mutableMap)
    }
}