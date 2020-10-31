package ru.primecare.pets.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ru.primecare.pets.databinding.ActivityScannerBinding
import ru.primecare.pets.ui.heplers.BarcodeScanner
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ScannerActivity : AppCompatActivity() {

    lateinit var binding: ActivityScannerBinding
    lateinit var barcodeScanner: BarcodeScanner
    lateinit var cameraExecutor: ExecutorService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        cameraExecutor = Executors.newSingleThreadExecutor()
        barcodeScanner = BarcodeScanner(this, binding.preview)
        barcodeScanner.viewCreated()

        barcodeScanner.onBarcodeDetected = {
            if(it.isNotEmpty())
                Log.d("SACNRES", it[0])
        }
        barcodeScanner.onTextDetected = {
            if(it.isNotEmpty())
                Log.d("SACNRES", it[0])
        }
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