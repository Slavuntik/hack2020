package ru.primecare.pets.data

data class Photo (
    val _id:String,
    val petId:String,
    val photos:List<String>
)