package com.timmitof.cityreport.core.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Base64
import java.io.ByteArrayOutputStream

fun isNetworkCheck(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> { return true }
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> { return true }
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> { return true }
        }
    }
    return false
}

fun bitmapToBase64(bm: Bitmap): String? {
    val baos = ByteArrayOutputStream()
    bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val b = baos.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}