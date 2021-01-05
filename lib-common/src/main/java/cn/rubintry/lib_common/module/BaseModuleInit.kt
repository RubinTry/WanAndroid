package cn.rubintry.lib_common.module

import android.app.Application
import android.util.Log
import cn.gorouter.api.launcher.GoRouter
import cn.rubintry.lib_network.http.CallAdapterType
import cn.rubintry.lib_network.http.NetApiManager
import com.blankj.utilcode.util.AppUtils

class BaseModuleInit : IModuleInit {
    private val TAG = this.javaClass.simpleName
    override fun init(application: Application) {
        Log.d(TAG, "开始集体初始化啦~~~ ，applicationName: ${application.javaClass.name}")

        NetApiManager.default?.init("https://www.wanandroid.com" , true , CallAdapterType.LiveData)

        if (AppUtils.isAppDebug()) {
            GoRouter.openLog()
        }
        GoRouter.setDebugable(true)
        GoRouter.init(application)


    }



}