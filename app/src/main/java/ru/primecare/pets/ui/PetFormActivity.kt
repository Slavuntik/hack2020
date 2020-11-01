package ru.primecare.pets.ui

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.primecare.pets.data.*
import ru.primecare.pets.databinding.ActivityPetFormBinding
import ru.primecare.pets.utils.decodeBase64ToByteArray
import ru.primecare.pets.ui.adapters.PetFormAdapter
import ru.primecare.pets.ui.heplers.ImageSelector
import ru.primecare.pets.utils.encodeBase64ToString
import java.io.ByteArrayOutputStream

class PetFormActivity : AppCompatActivity() {

    lateinit var binding: ActivityPetFormBinding
    val dataProvider = DataProvider()
    var data:PetFullInfo?=null
    lateinit var imageSelector: ImageSelector
    val adapter:PetFormAdapter = PetFormAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.fields.adapter = adapter
        binding.takePicture.setOnClickListener {
            startActivityForResult(imageSelector.prepareIntent(), 1)
        }
        intent.getStringExtra("cardNumber")?.let {
            lifecycleScope.launch {
                data = loadData(it)
                data?.let {
                    val imgLoaded = dataProvider.net.loadImage(it._id)
                    val img:ByteArray?
                    if(!imgLoaded.isNullOrEmpty()) {
                        img = imgLoaded.decodeBase64ToByteArray()
                    }else{
                        img = data?.petData?.find { it.cellIndex==999 }?.cellValue?.decodeBase64ToByteArray()
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

        binding.save.setOnClickListener {
            adapter.data.forEachIndexed() {index,field->
                data!!.petData[index].cellValue = field.cellValue
            }
            if(data!!._id==""){
                dataProvider.add(data!!)
            }else{
                dataProvider.update(data!!)
            }

        }

        if(data == null){
            data = dataProvider.getBaseForm()
            adapter.data.addAll(data!!.petData.subList(0,data!!.petData.size-1))
            binding.loading.visibility=View.GONE
        }
        imageSelector = ImageSelector(this, "Выберите изображение", 1)
    }

    suspend fun loadData(cardNumber:String):PetFullInfo?{
        return dataProvider.getPetByCardName(cardNumber)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                val imageUri = imageSelector.onActivityResult(requestCode, resultCode, data)
                if(imageUri != null){
                    GlobalScope.launch {
                        val bitmap = withContext(Dispatchers.IO){Glide.with(this@PetFormActivity).asBitmap()
                                .load(imageUri).submit(800,800).get()
                        }
                        val baos = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
                        val imageBytes: ByteArray = baos.toByteArray()
                        this@PetFormActivity.data!!.petData[this@PetFormActivity.data!!.petData.size-1].cellValue =imageBytes.encodeBase64ToString()
                        withContext(Dispatchers.Main){
                            binding.photo.setImageBitmap(bitmap)
                        }

                    }

                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}