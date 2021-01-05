package cn.rubintry.common.utils.http

import java.util.concurrent.TimeUnit

/**
 * @author logcat
 */
class BaseHttp {
    var baseUrl: String? = null
    private var connectTimeout: Long? = null
    private var readTimeout: Long? = null
    private var writeTimeout: Long? = null

    constructor() {}
    constructor(baseUrl: String?) {
        this.baseUrl = baseUrl
    }

    fun getConnectTimeout(): Long? {
        return connectTimeout
    }

    fun setConnectTimeout(connectTimeout: Long?) {
        setConnectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
    }

    fun setConnectTimeout(
        connectTimeout: Long?,
        unit: TimeUnit
    ) {
        this.connectTimeout = getTimeOut(connectTimeout, unit)
    }

    fun getReadTimeout(): Long? {
        return readTimeout
    }

    fun setReadTimeout(readTimeout: Long?) {
        setReadTimeout(readTimeout, TimeUnit.MILLISECONDS)
    }

    fun setReadTimeout(
        readTimeout: Long?,
        unit: TimeUnit
    ) {
        this.readTimeout = getTimeOut(readTimeout, unit)
    }

    fun getWriteTimeout(): Long? {
        return writeTimeout
    }

    fun setWriteTimeout(writeTimeout: Long?) {
        setWriteTimeout(writeTimeout, TimeUnit.MILLISECONDS)
    }

    fun setWriteTimeout(
        writeTimeout: Long?,
        unit: TimeUnit
    ) {
        this.writeTimeout = getTimeOut(writeTimeout, unit)
    }

    private fun getTimeOut(
        timeout: Long?,
        unit: TimeUnit
    ): Long {
        var resultTime: Long = 0
        when (unit) {
            TimeUnit.NANOSECONDS -> resultTime =
                unit.convert(timeout!!, TimeUnit.NANOSECONDS)
            TimeUnit.MICROSECONDS -> resultTime =
                unit.convert(timeout!!, TimeUnit.MICROSECONDS)
            TimeUnit.MILLISECONDS -> resultTime =
                unit.convert(timeout!!, TimeUnit.MILLISECONDS)
            TimeUnit.SECONDS -> resultTime =
                unit.convert(timeout!!, TimeUnit.SECONDS)
            TimeUnit.MINUTES -> resultTime =
                unit.convert(timeout!!, TimeUnit.MINUTES)
            TimeUnit.HOURS -> resultTime =
                unit.convert(timeout!!, TimeUnit.HOURS)
            TimeUnit.DAYS -> resultTime =
                unit.convert(timeout!!, TimeUnit.DAYS)
            else -> {
            }
        }
        return resultTime
    }
}