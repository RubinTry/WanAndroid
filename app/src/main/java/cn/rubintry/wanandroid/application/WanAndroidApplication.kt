package cn.rubintry.wanandroid.application

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDexApplication
import cn.gorouter.api.launcher.GoRouter
import cn.rubintry.common.utils.Utils
import cn.rubintry.common.utils.db.SharedPreferencesUtils
import cn.rubintry.common.utils.http.BaseHttp
import cn.rubintry.common.utils.http.OkHttpUtils

/**
 * @author logcat
 */
class WanAndroidApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        SharedPreferencesUtils.instance!!.initialize(this , "WanAndroidDataSharedPreferences" , Context.MODE_PRIVATE)

        val config = BaseHttp()
        config.baseUrl = "https://www.wanandroid.com"
        config?.setConnectTimeout(5000L)
        config?.setReadTimeout(5000L)
        config?.setWriteTimeout(5000L)
        OkHttpUtils.instance?.initialize(config)

        GoRouter.openLog()
        GoRouter.init(this)
        Utils.init(this)
    }
}