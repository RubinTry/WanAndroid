package cn.rubintry.common.utils

import android.app.Application
import android.content.Context

/**
 * @author logcat
 */
class Utils {
    companion object{
        var applicationContext: Context? = null
            private set
            get

        fun init(application: Application) {
            applicationContext = application.applicationContext
        }
    }





}