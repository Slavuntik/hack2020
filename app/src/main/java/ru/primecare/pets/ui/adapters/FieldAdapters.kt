package ru.primecare.pets.ui.adapters

import android.content.Context
import android.widget.ArrayAdapter
import ru.primecare.pets.data.*


fun getAdapters(context:Context):Map<Int,ArrayAdapter<String>>{
    return mapOf<Int, ArrayAdapter<String>>(
            1 to ArrayAdapter<String>(context,android.R.layout.simple_dropdown_item_1line,SPECIES),
            5 to ArrayAdapter<String>(context,android.R.layout.simple_dropdown_item_1line, GENDERS),
            6 to ArrayAdapter<String>(context,android.R.layout.simple_dropdown_item_1line, CATBREEDS.plus(DOGBREEDS)),
            7 to ArrayAdapter<String>(context,android.R.layout.simple_dropdown_item_1line, CATCOLORS.plus(DOGCOLORS)),
            8 to ArrayAdapter<String>(context,android.R.layout.simple_dropdown_item_1line, CATHAIR.plus(DOGHAIR)),
            9 to ArrayAdapter<String>(context,android.R.layout.simple_dropdown_item_1line, EARS),
            10 to ArrayAdapter<String>(context,android.R.layout.simple_dropdown_item_1line, TAILS)


    )
}
