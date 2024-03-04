package com.example.tipapp.util

fun calculateTotalPerPerson (bill: Double, split :Int, tip: Int): Double{

    return (bill + tip)/split
}