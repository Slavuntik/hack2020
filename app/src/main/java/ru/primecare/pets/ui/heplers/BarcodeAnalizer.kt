package ru.primecare.pets.ui.heplers

import android.annotation.SuppressLint
import android.util.Log
import android.util.Size
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
/*
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions*/


class BarcodeAnalizer(
        val onDetect:(list:List<String>)->Unit,
        val onTextDetect:(lidt:List<String>)->Unit,
        val onError:(e:Exception)->Unit = {}
) : ImageAnalysis.Analyzer {
    companion object{
        var size:Size = Size(-1,-1)
    }
/*
    val options = FirebaseVisionBarcodeDetectorOptions.Builder()
            .setBarcodeFormats(
                    FirebaseVisionBarcode.FORMAT_QR_CODE)
            .build()
    val textDetector = FirebaseVision.getInstance().cloudTextRecognizer
    val textOptions = FirebaseVisionCloudTextRecognizerOptions.Builder()
        .setLanguageHints(listOf("en", "ru"))
        .build()

    val barcodeOptions = FirebaseVisionBarcodeDetectorOptions.Builder()
        .setBarcodeFormats(
            FirebaseVisionBarcode.FORMAT_QR_CODE)
        .build()
    val barcodeDetecort = FirebaseVision.getInstance().getVisionBarcodeDetector(barcodeOptions)

    private fun degreesToFirebaseRotation(degrees: Int): Int = when(degrees) {
        0 -> FirebaseVisionImageMetadata.ROTATION_0
        90 -> FirebaseVisionImageMetadata.ROTATION_90
        180 -> FirebaseVisionImageMetadata.ROTATION_180
        270 -> FirebaseVisionImageMetadata.ROTATION_270
        else -> throw Exception("Rotation must be 0, 90, 180, or 270.")
    }
*/

    val textDetector = TextRecognition.getClient()
    val barcodeDetector = BarcodeScanning.getClient()

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(imageProxy: ImageProxy) {

        val mediaImage = imageProxy.image
        Log.d("ROTATION", imageProxy.imageInfo.rotationDegrees.toString())

        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            textDetector.process(image).addOnSuccessListener {
                onTextDetect(it.textBlocks.map { it.text })
                Log.d("asdasdad", it.text)
            }.addOnFailureListener {
                onError(it)
            }.addOnCompleteListener{
                imageProxy.close()
            }
            /*barcodeDetector.process(image).addOnSuccessListener {
                val ret =it.map { it.rawValue?:"" }
                onDetect(ret)
            }.addOnFailureListener {
                onError(it)
            }*/

            //Log.d("barcodes", result.toString())
        }else{
            imageProxy.close()
        }

    }
}