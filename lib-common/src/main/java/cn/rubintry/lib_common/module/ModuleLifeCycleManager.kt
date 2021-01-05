package cn.rubintry.lib_common.module

import android.app.Application
import cn.rubintry.lib_common.module.IModuleInit
import cn.rubintry.lib_common.module.LifecycleReflects
import java.lang.Exception

class ModuleLifeCycleManager {
    companion object {
        @Volatile
        var instance: ModuleLifeCycleManager? = null
            get() {
                if (field == null) {
                    synchronized(ModuleLifeCycleManager::class.java) {
                        if (field == null) {
                            field = ModuleLifeCycleManager()
                        }
                    }
                }
                return field
            }
    }


    fun init(application: Application){
        for (initModuleName in LifecycleReflects.initModuleNames) {
            try {
                val moduleClazz = Class.forName(initModuleName)
                val iModuleInit = moduleClazz.newInstance() as IModuleInit
                iModuleInit.init(application)
            }catch (ex: Exception){
                ex.printStackTrace()
            }
        }
    }
}