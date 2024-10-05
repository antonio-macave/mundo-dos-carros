package mz.co.macave.mundodoscarros.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object NetworkUtils {
    fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        connectivityManager?.run {
            val networkCapabilities = activeNetwork ?: return false
            val activeNw = getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                activeNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }
        return result
    }
}