package ru.primecare.pets.ui.heplers

import android.app.Activity
import android.content.Context
import android.graphics.RectF
import android.hardware.display.DisplayManager
import android.util.DisplayMetrics
import android.util.Size
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.text.Text
import java.util.concurrent.ExecutorService
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class BarcodeScanner(
    val activity:AppCompatActivity,
    val viewFinder: PreviewView
) {
    var displayId: Int = -1
    var lensFacing: Int = CameraSelector.LENS_FACING_BACK
    var preview: Preview? = null
    var imageAnalyzer: ImageAnalysis? = null
    var camera: Camera? = null
    var torchEnabled = false
        private set

    var onBarcodeDetected : (list:List<String>)->Unit = {}
    var onTextDetected : (Text)->Unit={}
    var onError: (e:java.lang.Exception)->Unit={}

    private val displayListener = object : DisplayManager.DisplayListener {
        override fun onDisplayAdded(displayId: Int) = Unit
        override fun onDisplayRemoved(displayId: Int) = Unit
        override fun onDisplayChanged(displayId: Int) = Unit
    }
    private val displayManager by lazy {
        activity.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
    }


    fun viewCreated(){
        displayManager.registerDisplayListener(displayListener, null)
    }

    fun destroyView(){
        displayManager.unregisterDisplayListener(displayListener)
    }

    fun bindCameraUseCases(cameraExecutor: ExecutorService) {
        // Get screen metrics used to setup camera for full screen resolution
        val metrics = DisplayMetrics().also { viewFinder.display?.getRealMetrics(it)/*display.getRealMetrics(it)*/ }
        val screenAspectRatio = aspectRatio(metrics.widthPixels, metrics.heightPixels)
        val rotation = viewFinder.display.rotation
        // Bind the CameraProvider to the LifeCycleOwner
        val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
        val cameraProviderFuture = ProcessCameraProvider.getInstance(activity)
        cameraProviderFuture.addListener(Runnable {
            // CameraProvider
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            // Preview
            preview = Preview.Builder()
                    // We request aspect ratio but no resolution
                    .setTargetAspectRatio(screenAspectRatio)
                    // Set initial target rotation
                    .setTargetRotation(rotation)
                    .build()
            // Attach the viewfinder's surface provider to preview use case
            preview?.setSurfaceProvider(viewFinder.surfaceProvider)
            // ImageAnalysis
            imageAnalyzer = ImageAnalysis.Builder()
                    // We request aspect ratio but no resolution
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .setTargetAspectRatio(screenAspectRatio)
                    // Set initial target rotation, we will have to call this again if rotation changes
                    // during the lifecycle of this use case
                    .setTargetRotation(rotation)
                    .build()
                    // The analyzer can then be assigned to the instance
                    .also {
                        it.setAnalyzer(cameraExecutor, BarcodeAnalizer(onBarcodeDetected, onTextDetected, onError))
                    }
            // Must unbind the use-cases before rebinding them
            cameraProvider.unbindAll()
            try {
                // A variable number of use-cases can be passed here -
                // camera provides access to CameraControl & CameraInfo
                camera = cameraProvider.bindToLifecycle(
                        activity, cameraSelector, imageAnalyzer, preview)//, imageAnalyzer)
            } catch(exc: Exception) {
            }
        }, ContextCompat.getMainExecutor(activity))
    }


    fun switchTorchState(){
        camera?.cameraControl?.enableTorch(!torchEnabled)
        torchEnabled = !torchEnabled
    }

    fun convertCoordinates(surfaceSize:Size, rect:RectF):RectF{
        var size = BarcodeAnalizer.size
        val newBounds = squareFrame(size)
        size = newBounds[0]
        val offsets = newBounds[1]
        val hScale:Float = surfaceSize.width.toFloat()/size.width
        val vScale:Float = surfaceSize.height.toFloat()/size.height
        val left = (rect.left-offsets.width)*hScale
        val top = (rect.top-offsets.height)*vScale
        val right = (rect.right-offsets.width)*hScale
        val bottom = (rect.bottom-offsets.height)*vScale
        return RectF(left, top, right, bottom)
    }

    fun squareFrame(size: Size):List<Size>{
        val lesser = if(size.width>size.height){size.height}else{size.width}
        val offsetX = (size.width-lesser)/2
        val offsetY = (size.height-lesser)/2
        val newSize = Size(size.width-offsetX*2, size.height-offsetY*2)
        val offcets = Size(offsetX, offsetY)
        return listOf(newSize, offcets)
    }

    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)
        if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE)) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }

    companion object {

        private const val RATIO_4_3_VALUE = 4.0 / 3.0
        private const val RATIO_16_9_VALUE = 16.0 / 9.0
    }
}