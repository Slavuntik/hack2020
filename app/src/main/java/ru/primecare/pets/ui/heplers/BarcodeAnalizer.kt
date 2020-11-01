package ru.primecare.pets.ui.heplers

import android.annotation.SuppressLint
import android.util.Log
import android.util.Size
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition

class BarcodeAnalizer(
    val onDetect:(list:List<String>)->Unit,
    val onTextDetect:(lidt: Text)->Unit,
    val onError:(e:Exception)->Unit = {}
) : ImageAnalysis.Analyzer {
    companion object{
        var size:Size = Size(-1,-1)
    }

    val textDetector = TextRecognition.getClient()
    val barcodeDetector = BarcodeScanning.getClient()

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(imageProxy: ImageProxy) {

        val mediaImage = imageProxy.image
        Log.d("ROTATION", imageProxy.imageInfo.rotationDegrees.toString())

        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            textDetector.process(image).addOnSuccessListener {
                onTextDetect(it)
                //Log.d("asdasdad", it.text)
            }.addOnFailureListener {
                onError(it)
            }.addOnCompleteListener{
                imageProxy.close()
            }
            /*barcodeDetector.process(image).addOnSuccessListener {
                val ret =it.map { it.rawValue?:"" }
                onDetect(ret)
                if(it.isNotEmpty())
                    Log.d("asdasdad", it[0]?.rawValue)
            }.addOnFailureListener {
                onError(it)
            }.addOnCompleteListener{
                imageProxy.close()
            }*/

            //Log.d("barcodes", result.toString())
        }else{
            imageProxy.close()
        }

    }
}