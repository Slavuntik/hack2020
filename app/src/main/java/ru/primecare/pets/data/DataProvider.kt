package ru.primecare.pets.data

import android.util.Log
import com.google.gson.Gson
import ru.primecare.pets.App
import ru.primecare.pets.ui.entities.PetListItemInfo
import ru.primecare.pets.utils.ConnectionChecker
import kotlin.random.Random

class DataProvider() {
    val connection = ConnectionChecker(App.context)

    val net = Net()

    suspend fun getPetList():List<PetListItemInfo>{
        loadDataIfEmpty()
        return petsList.map { PetListItemInfo(it)}
    }

    fun getBaseForm():PetFullInfo{
        return Gson().fromJson(baseFormJsonString, PetFullInfo::class.java)
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

    fun add(info:PetFullInfo){
        info._id = Random.nextInt(100,999).toString()
        petsList.add(0,info)
    }

    fun update(info:PetFullInfo){
        val old = petsList.find { it._id.equals(info._id, true) ||
        it.petData[0].cellValue.equals(info.petData[0].cellValue, true) &&
        it.petData[14].cellValue.equals(info.petData[14].cellValue, true)}
        if(old!=null){
            Log.d("Found", "asdasd")
            val index = petsList.indexOf(old)
            petsList[index] = info
            petsList[index]._id=Random.nextInt(100,999).toString()
            Log.d("Found", petsList[index]._id)
            Log.d("Found", petsList[index].petData[4].cellValue)
        }
    }

    private suspend fun loadDataIfEmpty(){
        if(petsList.size == 0  && connection.checkConnection()){
            petsList.addAll(net.getAllPets().filter { it.petData[16].cellValue=="ВРАЧ 1" })
        }
    }

    companion object{
        val petsList = mutableListOf<PetFullInfo>()
    }
}