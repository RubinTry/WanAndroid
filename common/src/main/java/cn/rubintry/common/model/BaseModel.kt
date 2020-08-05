package cn.rubintry.common.model

import cn.rubintry.common.utils.http.IMonitor

/**
 * @author logcat
 */
class BaseModel<T> : IMonitor {
    var data: T? = null
        get() = field
        private set
    override var errMsg: String? = null
        get() = field
        set

    override var code = 0
        get() = field
        set

    var errorMsg : String = ""
        get() = field
        set

    var errorCode : Int ?= null
        get() = field
        set


    fun setData(data: T) {
        this.data = data
    }

    override fun success(): Boolean {
        return code == 0
    }
}