package cn.rubintry.module_login

import android.app.Application
import cn.rubintry.lib_common.module.ModuleLifeCycleManager
import cn.rubintry.lib_network.http.CallAdapterType
import cn.rubintry.lib_network.http.NetApiManager

class LoginApp: Application() {

    override fun onCreate() {
        super.onCreate()
        ModuleLifeCycleManager.instance?.init(this)
    }
}