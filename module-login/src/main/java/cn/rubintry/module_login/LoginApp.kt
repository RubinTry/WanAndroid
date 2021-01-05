package cn.rubintry.module_login

import android.app.Application
import cn.rubintry.lib_network.http.CallAdapterType
import cn.rubintry.lib_network.http.NetApiManager

class LoginApp: Application() {

    override fun onCreate() {
        super.onCreate()
        NetApiManager.default?.init("https://www.wanandroid.com" , true , CallAdapterType.LiveData)
    }
}