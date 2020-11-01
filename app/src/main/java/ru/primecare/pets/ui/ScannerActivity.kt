package ru.primecare.pets.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import ru.primecare.pets.databinding.ActivityScannerBinding
import ru.primecare.pets.ui.heplers.BarcodeScanner
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ScannerActivity : AppCompatActivity() {

    lateinit var binding: ActivityScannerBinding
    lateinit var barcodeScanner: BarcodeScanner
    lateinit var cameraExecutor: ExecutorService
    var dialogDisplayed:Boolean = false
    val charRegexp = "\\D".toRegex()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        cameraExecutor = Executors.newSingleThreadExecutor()
        barcodeScanner = BarcodeScanner(this, binding.preview)
        barcodeScanner.viewCreated()

        barcodeScanner.onBarcodeDetected = {

            Log.d("SACNRES", it[0])
        }
        barcodeScanner.onTextDetected = {
            if(!dialogDisplayed) {
                val numbers = mutableListOf<String>()
                it.textBlocks.forEach {
                    it.lines.forEach {
                        it.elements.forEach {
                            val num = it.text.replace(charRegexp, "")
                            if(num.length == 15){
                                numbers.add(num)
                            }

                        }
                    }
                }
                if(numbers.size == 1){
                    showConfirmOneItem(numbers[0])
                }else if(numbers.size>1){
                    showSelectionList(numbers)
                }
            }

        }
    }

    fun showConfirmOneItem(code:String){
        dialogDisplayed = true
        AlertDialog.Builder(this)
            .setTitle("Это идентификатор который вам нужен?")
            .setMessage(code).setPositiveButton(android.R.string.yes) { _, _ ->
                result(code)
            }.setNegativeButton(android.R.string.no) { _, _ ->
                dialogDisplayed = false
            }.show()
    }

    fun showSelectionList(items:List<String>){
        dialogDisplayed = true
        val builder = AlertDialog.Builder(this)
            builder.setTitle("Выберите идентификатор кторый вам нужен")
            builder.setItems(items.toTypedArray()) { _, pos ->
                result(items[pos])
            }
            builder.setNegativeButton(android.R.string.no) { _, _ ->
                dialogDisplayed = false
            }.show()
    }

    private fun result(code:String){
        val result = Intent()
        result.setData(Uri.parse(code))
        setResult(RESULT_OK, result)
        finish()
    }

    override fun onStart() {
        super.onStart()
        cameraExecutor = Executors.newSingleThreadExecutor()
        binding.preview.post {
            barcodeScanner.displayId = binding.preview.display.displayId
            barcodeScanner.bindCameraUseCases(cameraExecutor)
        }
    }



    override fun onStop() {
        super.onStop()
        cameraExecutor.shutdown()
    }

    override fun onDestroy() {
        super.onDestroy()
        barcodeScanner.destroyView()
        cameraExecutor.shutdown()
    }

}