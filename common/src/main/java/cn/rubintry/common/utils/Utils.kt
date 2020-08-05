package cn.rubintry.common.utils

import android.app.Application
import android.content.Context

/**
 * @author logcat
 */
object Utils {
    var applicationContext: Context? = null
        private set

    fun init(application: Application) {
        applicationContext = application.applicationContext
    }



}