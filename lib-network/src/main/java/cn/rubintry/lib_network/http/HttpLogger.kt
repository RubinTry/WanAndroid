package cn.rubintry.lib_network.http

import android.util.Log
import okhttp3.logging.HttpLoggingInterceptor

class HttpLogger : HttpLoggingInterceptor.Logger {
    private val TAG = this.javaClass.simpleName
    override fun log(message: String) {
        Log.i(TAG, message)
    }
}