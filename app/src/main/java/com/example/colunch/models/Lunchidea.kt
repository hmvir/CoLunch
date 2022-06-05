package com.example.colunch.models

class Lunchidea(
    val restaurant: Restaurant,
    val bestellzeit: String,
    val bezahlungsart: String,
    val gesperrt: Boolean,
    val teilnehmer: Array<Map<String,String>>
) {
}