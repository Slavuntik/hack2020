package ru.primecare.pets.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import ru.primecare.pets.data.Net

class ConnectionChecker(context: Context) {
    private val connectivityManager:ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun checkConnection():Boolean{
        return checkDeviceNetworkEnabled()
    }

    private fun checkDeviceNetworkEnabled():Boolean{
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
}