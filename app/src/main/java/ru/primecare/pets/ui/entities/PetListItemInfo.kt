package ru.primecare.pets.ui.entities

import ru.primecare.pets.data.PetBasicInfo
import ru.primecare.pets.data.PetFullInfo

data class PetListItemInfo(
        val petId:String,
        val cardNumber: String,
        val identNumber:String,
        val photo:String,
        val species:String,
        val name:String,
        val breed:String
) {


    constructor(petFullInfo:PetFullInfo):this(
            petId=petFullInfo._id,
            cardNumber=petFullInfo.petData[0].cellValue,
            identNumber=petFullInfo.petData[14].cellValue,
            photo=petFullInfo.petData.find { it.cellIndex==999 }?.cellValue?:"",
            species=petFullInfo.petData[1].cellValue,
            name=petFullInfo.petData[4].cellValue,
            breed=petFullInfo.petData[6].cellValue
    )
}