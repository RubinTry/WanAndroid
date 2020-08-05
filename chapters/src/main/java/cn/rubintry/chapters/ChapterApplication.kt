package cn.rubintry.chapters

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
class ChapterApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        var config: BaseHttp = BaseHttp()
        config.baseUrl = "https://www.wanandroid.com"
        config.connectTimeout = 5000L
        config.readTimeout = 5000L
        config.writeTimeout = 5000L
        OkHttpUtils.getInstance().initialize(config)
        GoRouter.openLog()
        GoRouter.init(this)
        Utils.init(this)

        SharedPreferencesUtils.instance!!.initialize(this , "WanAndroidDataSharedPreferences" , Context.MODE_PRIVATE)
    }
}