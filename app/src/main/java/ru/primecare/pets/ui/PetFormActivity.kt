package ru.primecare.pets.ui

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.primecare.pets.data.*
import ru.primecare.pets.databinding.ActivityPetFormBinding
import ru.primecare.pets.decodeBase64ToByteArray
import ru.primecare.pets.ui.adapters.PetFormAdapter

class PetFormActivity : AppCompatActivity() {

    lateinit var binding: ActivityPetFormBinding
    val dataProvider = DataProvider()
    var data:PetFullInfo?=null

    val adapter:PetFormAdapter = PetFormAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.fields.adapter = adapter
        intent.getStringExtra("cardNumber")?.let {
            lifecycleScope.launch {
                data = loadData(it)
                data?.let {
                    val imgLoaded = dataProvider.net.loadImage(it._id)
                    val img:ByteArray?
                    if(!imgLoaded.isNullOrEmpty()) {
                        img = imgLoaded.decodeBase64ToByteArray()
                    }else{
                        img = data?.petData?.find { it.cellIndex==666 }?.cellHeader?.decodeBase64ToByteArray()
                    }
                    if(img!=null){
                        withContext(Dispatchers.Main) {
                            Glide.with(binding.photo.context)
                                    .load(img)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .into(binding.photo)
                        }
                    }

                    withContext(Dispatchers.Main){
                        adapter.data.addAll(it.petData.subList(0,it.petData.size-1))
                        adapter.notifyDataSetChanged()
                        binding.loading.visibility = View.GONE

                    }
                }

            }
        }

    }

    suspend fun loadData(cardNumber:String):PetFullInfo?{
        return dataProvider.getPetByCardName(cardNumber)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}