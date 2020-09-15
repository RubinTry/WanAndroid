package cn.rubintry.common.model

import cn.rubintry.common.utils.http.IMonitor

/**
 * @author logcat
 */
class BaseModel<T> : IMonitor {
    override val errMsg: String
        get() = errorMsg
    override val code: Int
        get() = errorCode

    var data: T? = null
        get() = field
        private set


    var errorMsg: String = ""
        get() = field
        set

    var errorCode: Int = 0
        get() = field
        set


    fun setData(data: T) {
        this.data = data
    }


    override fun success(): Boolean {
        return code == 0
    }
}