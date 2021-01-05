package cn.rubintry.wanandroid

import android.app.Application
import cn.rubintry.lib_common.module.ModuleLifeCycleManager

class WanAndroidApp : Application(){

    override fun onCreate() {
        super.onCreate()
        ModuleLifeCycleManager.instance?.init(this)
    }
}