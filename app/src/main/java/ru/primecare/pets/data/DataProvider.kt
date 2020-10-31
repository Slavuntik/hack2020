package ru.primecare.pets.data

import ru.primecare.pets.App
import ru.primecare.pets.ui.entities.PetListItemInfo
import ru.primecare.pets.utils.ConnectionChecker

class DataProvider() {
    val connection = ConnectionChecker(App.context)

    val net = Net()

    suspend fun getPetList():List<PetListItemInfo>{
        loadDataIfEmpty()
        return petsList.map { PetListItemInfo(it)}
    }

    suspend fun getPetByCardName(cardNumber:String):PetFullInfo?{
        loadDataIfEmpty()
        return petsList.find { it.petData[0].cellValue == cardNumber }
    }

    fun filter(code:String):List<PetListItemInfo>{
        return if(petsList.find { it.petData[0].cellValue.equals(code, true)} != null){
            petsList.filter { it.petData[0].cellValue.equals(code, true) }.map { PetListItemInfo(it) }
        }else if(petsList.find { it.petData[14].cellValue.equals(code, true)} != null){
            petsList.filter { it.petData[14].cellValue.equals(code, true) }.map { PetListItemInfo(it) }
        }else{
            emptyList()
        }
    }

    private suspend fun loadDataIfEmpty(){
        if(petsList.size == 0  && connection.checkConnection()){
            petsList.addAll(net.getAllPets())
        }
    }

    companion object{
        val petsList = mutableListOf<PetFullInfo>()
    }
}