package ru.primecare.pets.ui.heplers

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import kotlin.system.exitProcess

class PermissionsManager(val context: AppCompatActivity) {

    var camera = false
    var awaiting = false
    var onPermissionGranted:(()->Unit) = {}
    val REQUEST_CAMERA_PERMISSION = 0

    fun permissionsGranted():Boolean{
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermissionResult(requestCode: Int,
                                permissions: Array<String>,
                                grantResults: IntArray){
        awaiting = false
        if(grantResults.size >0){
            for (i in grantResults){
                if(i != PackageManager.PERMISSION_GRANTED){
                    requestPermissions()
                }
            }
        }
        if(permissionsGranted())
            onPermissionGranted()
    }

    fun requestPermissions(){
        val ad: AlertDialog.Builder = AlertDialog.Builder(context)
        if(awaiting) return
        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ){
            if(!camera){
                awaiting = true
                ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
                camera = true
            }else if(ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.CAMERA)){
                awaiting = true
                ad.setTitle("Разрешение на использование камеры")
                ad.setMessage("Для работы приложения необходимо разрешение на использование камеры.\nЗапросить ещё раз?")
                ad.setPositiveButton("Да"){dialog, which->
                    ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
                }
                ad.setNegativeButton("Нет(приложение будет закрыто)"){dialog, which->
                    exitProcess(0)
                }
                ad.show()
            } else {
                awaiting = true
                ad.setTitle("Разрешение на использование камеры")
                ad.setMessage("Для работы приложения необходимо разрешение на использование камеры.\nПожалуйста перейдите в настройки и дайте разрешение на использование камеры")
                ad.setPositiveButton("Да") { dialog, which ->
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    val uri: Uri = Uri.fromParts("package", context.getPackageName(), null)
                    intent.data = uri
                    context.startActivityForResult(intent, 22)
                }
                ad.setNegativeButton("Нет(приложение будет закрыто)") { dialog, which ->
                    exitProcess(0)
                }
                ad.show()
            }
        }
    }
}