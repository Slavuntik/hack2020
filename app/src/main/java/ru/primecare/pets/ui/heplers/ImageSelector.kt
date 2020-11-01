package ru.primecare.pets.ui.heplers

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class ImageSelector(val context: Context, val title:String, val requestCode:Int) {

    lateinit var outputFileUri:Uri
    lateinit var currentPhotoPath: String

    fun prepareIntent(): Intent {

        // Determine Uri of camera image to save.
        val file = createImageFile()
        outputFileUri = getFileUri(file)
        // Camera.
        val cameraIntents = mutableListOf<Intent>()//List<Intent> = ArrayList()
        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val packageManager: PackageManager = context.packageManager
        val listCam: List<ResolveInfo> = packageManager.queryIntentActivities(captureIntent, 0)
        for (res in listCam) {
            val packageName: String = res.activityInfo.packageName
            val intent = Intent(captureIntent)
            intent.component = ComponentName(packageName, res.activityInfo.name)
            intent.setPackage(packageName)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
            intent.resolveActivity(packageManager)
            cameraIntents.add(intent)
        }

        // Filesystem.
        val galleryIntent = Intent()
        galleryIntent.type = "image/*"
        galleryIntent.action = Intent.ACTION_PICK

        // Chooser of filesystem options.
        val chooserIntent = Intent.createChooser(galleryIntent, title)

        // Add the camera options.
        chooserIntent.putExtra(
            Intent.EXTRA_INITIAL_INTENTS,
            cameraIntents.toTypedArray()
        )
        return chooserIntent
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Uri? {
        if (requestCode == this.requestCode) {
            val isCamera = MediaStore.ACTION_IMAGE_CAPTURE == data?.action || data == null
            return if (isCamera) {
                outputFileUri
            } else {
                Uri.parse(data?.data.toString())
            }
        }else{
            return null
        }
    }



    private fun getFileUri(file:File):Uri{
        return FileProvider.getUriForFile(
            context,
            "ru.primecare.pets.fileprovider",
            file
        )
    }

    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "PNG_${timeStamp}_", /* prefix */
            ".png", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    }