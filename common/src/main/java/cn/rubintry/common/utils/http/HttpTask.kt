package cn.rubintry.common.utils.http

import okhttp3.MediaType

/**
 * @author logcat
 */
class HttpTask {
    var interfacePath: String? = null
    var requestType: RequestType? = null


    //默认使用同步请求
    var methodType: MethodType? = null
        get() =//默认使用同步请求
            if (field == null) {
                MethodType.ASYNCHRONOUS
            } else field


    private var parameters: MutableMap<String, String> = HashMap()


    var mediaType: MediaType? = null
        get() = if (field == null) {
            MediaTypePool.DEFAULT
        } else field


    fun getParameters(): MutableMap<String, String> {
        if (parameters == null) {
            parameters = HashMap()
        }
        return parameters!!
    }


    fun setParameters(parameters: MutableMap<String, String>) {
        this.parameters = parameters
    }


    fun clearParameters() {
        if(parameters != null){
            parameters.clear()
        }
    }


    init {
        if (parameters == null) {
            parameters = HashMap()
        }
    }
}