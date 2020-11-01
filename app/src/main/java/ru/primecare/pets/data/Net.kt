package ru.primecare.pets.data

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*

class Net {
    val BASE_URL= "https://dtl2020.herokuapp.com"
    var client: HttpClient = HttpClient(){
        install(JsonFeature){
            serializer = GsonSerializer{
                disableHtmlEscaping()
            }
        }
        install(Logging){
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
        expectSuccess = false
    }

    suspend fun getCatalogue(name:String):CatalogueEntity{
        return client.get("$BASE_URL/getdics?header=$name")
    }

    suspend fun getAllPets():List<PetFullInfo>{
        try {
            return client.get("https://dtl2020.herokuapp.com/pets")
        }catch(e:Throwable){
            return emptyList()
        }

    }

    suspend fun loadImage(id:String):String{
        try{
            val result = client.get<List<Photo>>("https://dtl2020.herokuapp.com/photos?petid=$id")
            if(result.isNotEmpty()){
                if(result[0].photos.isNotEmpty()){
                    return result[0].photos[0]
                }
                return ""
            }
            return ""
        }catch (e:Throwable){
            return ""
        }

    }
}