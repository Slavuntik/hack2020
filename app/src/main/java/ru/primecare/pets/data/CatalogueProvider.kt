package ru.primecare.pets.data

class CatalogueProvider {

    suspend fun getGenders():List<String> = GENDERS
    suspend fun getSpecies():List<String> = SPECIES
    suspend fun getCatBreeds():List<String> = CATBREEDS
    suspend fun getDogBreeds():List<String> = DOGBREEDS
    suspend fun getAllBreeds():List<String> = CATBREEDS.plus(DOGBREEDS)
    suspend fun getCatHair():List<String> = CATHAIR
    suspend fun getDogHair():List<String> = DOGHAIR
    suspend fun getAllHair():List<String> = DOGHAIR.plus(CATHAIR)
    suspend fun getEars():List<String> = EARS
    suspend fun getTails():List<String> = TAILS
    suspend fun getCatColors():List<String> = CATCOLORS
    suspend fun getDogColors():List<String> = DOGCOLORS
}