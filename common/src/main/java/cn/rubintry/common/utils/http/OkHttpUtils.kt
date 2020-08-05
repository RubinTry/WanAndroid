package cn.rubintry.common.utils.http

import android.util.Log
import cn.rubintry.common.utils.GsonUtils
import cn.rubintry.common.utils.logger.OkLogger
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.tls.HandshakeCertificates
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.net.SocketTimeoutException
import java.net.URLDecoder
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

/**
 * @author logcat
 */
class OkHttpUtils private constructor() {
    private val httpTask: HttpTask = HttpTask()
    private var config: BaseHttp? = null
    private var defaultClient: OkHttpClient? = null

    /**
     * 初始化参数
     *
     * @param config
     */
    fun initialize(config: BaseHttp?) {
        this.config = config
    }

    /**
     * 设置mediaType
     *
     * @param mediaType
     * @return
     */
    fun setMediaType(mediaType: MediaType?): OkHttpUtils {
        httpTask.mediaType = mediaType
        return this
    }

    /**
     * 添加参数
     *
     * @param key
     * @param value
     * @return
     */
    fun addParameters(key: String?, value: String?): OkHttpUtils {
        httpTask.getParameters()?.put(key!!, value!!)
        return this
    }

    /**
     * 将请求方式设为get
     *
     * @param path
     * @return
     */
    operator fun get(path: String?): OkHttpUtils {
        httpTask.interfacePath = path
        httpTask.requestType = RequestType.GET
        httpTask.clearParameters()
        return this
    }

    /**
     * 将请求方式设为get并携带参数
     *
     * @param path
     * @param parameters
     * @return
     */
    fun get(
        path: String?,
        parameters: MutableMap<String, String>
    ): OkHttpUtils {
        httpTask.interfacePath = path
        httpTask.requestType = RequestType.GET
        httpTask.setParameters(parameters)
        return this
    }

    /**
     * 将请求方式设为post
     *
     * @return
     */
    fun post(path: String?): OkHttpUtils {
        httpTask.interfacePath = path
        httpTask.requestType = RequestType.POST
        httpTask.clearParameters()
        return this
    }

    /**
     * 将请求方式设为post并携带参数
     *
     * @param path
     * @param parameters
     * @return
     */
    fun post(
        path: String?,
        parameters: MutableMap<String, String>
    ): OkHttpUtils {
        httpTask.interfacePath = path
        httpTask.requestType = RequestType.POST
        httpTask.setParameters(parameters)
        return this
    }

    /**
     * 请求方式  SYNCHRONIZE 同步请求  ASYNCHRONOUS 异步请求
     *
     * @param methodType
     * @return
     */
    fun setMethodType(methodType: MethodType?): OkHttpUtils {
        httpTask.methodType = methodType
        return this
    }

    /**
     * 发起一个请求
     *
     * @param responseCallback
     */
    fun <T> request(responseCallback: ResponseCallback<T>) {
        val builder = Request.Builder()
        when (httpTask.requestType) {
            RequestType.GET -> {
                val getUrl = getWholeUrl(
                    config?.baseUrl!!,
                    httpTask?.interfacePath!!,
                    httpTask.getParameters()
                )
                builder.url(getUrl).get()
            }
            RequestType.POST -> {
                //使用form表单方式进行参数拼接
                val parameters : Map<String , String> ? =
                    httpTask.getParameters()
                val bodyBuilder =
                    FormBody.Builder(Charset.defaultCharset())
                for (key in parameters?.keys!!) {
                    bodyBuilder.add(key, parameters?.get(key)!!)
                }
                val postUrl = config!!.baseUrl + httpTask.interfacePath
                builder.url(postUrl).post(bodyBuilder.build())
            }
            else -> {
            }
        }

        //使用专门的网络请求线程池进行请求网络
        HttpExecutors.instance?.networkIO()?.execute {
            try {
                val modelType = responseCallback.modelType
                try {
                    requireNotNull(modelType) { "No type-model , please set it!!!" }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                if (httpTask.methodType == MethodType.SYNCHRONIZE) {
                    //这里发起同步请求
                    execute(builder.build(), config, responseCallback, modelType!!)
                } else {
                    //这里发起异步请求
                    enqueue(builder.build(), config, responseCallback, modelType!!)
                }
            } catch (e: Exception) {
                OkLogger.error("Exception happen : ", e)
            }
        }
    }



    private fun <T> enqueue(
        request: Request,
        config: BaseHttp?,
        responseCallback: ResponseCallback<T>,
        modelType: ParameterizedType
    ) {
        val call = getDefaultClient(config)!!.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                if(e is SocketTimeoutException){
                    responseCallback?.onFailed(java.lang.Exception("请求超时"), null)
                }else{
                    responseCallback?.onFailed(e, null)
                }

            }

            @Throws(IOException::class)
            override fun onResponse(
                call: Call,
                response: Response
            ) {
                val responseJsonStr = response.body!!.string()
                val bodyObj =
                    GsonUtils.getDefault().analyze(responseJsonStr , modelType)
                if (IMonitor::class.java.isAssignableFrom(bodyObj.javaClass)) {
                    val iMonitor = bodyObj as IMonitor
                    if (iMonitor.success()) {
                        responseCallback?.onSuccess(bodyObj as T)
                    } else {
                        responseCallback?.onFailed(
                            ServerException(
                                iMonitor?.errMsg!!,
                                iMonitor.code
                            ), iMonitor as T
                        )
                    }
                } else {
                    response.body!!.close()
                    throw IllegalAccessError("Please implements interface IMonitor before use this Util")
                }
                response.body!!.close()
            }
        })
    }

    /**
     * 同步执行请求
     *
     * @param request   发起的请求
     * @param config    http基本配置
     * @param callback  响应回调函数
     * @param modelType 响应的数据的泛型
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun <T> execute(
        request: Request,
        config: BaseHttp?,
        callback: ResponseCallback<T>?,
        modelType: ParameterizedType
    ) {
        val call = getDefaultClient(config)!!.newCall(request)
        val response = call.execute()
        //request success
        val responseJsonStr = response.body!!.string()
        //Convert response info to result object.
        val obj =
            GsonUtils.getDefault().analyze(responseJsonStr , modelType)
        if (response.code == RESPONSE_OK) {
            if (IMonitor::class.java.isAssignableFrom(obj.javaClass)) {
                val iMonitor = obj as IMonitor
                if (iMonitor.success()) {
                    callback?.onSuccess(obj as T)
                } else {
                    callback?.onFailed(
                        ConvertException("Convert failed , target type is" + modelType!!.typeName + " but jsonStr is " + responseJsonStr),
                        iMonitor as T
                    )
                }
            } else {
                response.body!!.close()
                throw IllegalAccessError("Please implements interface IMonitor before use this Util")
            }
            response.body!!.close()
        } else {
            OkLogger.error("no response , result code is " + response.code)
            if (callback != null) {
                if (IMonitor::class.java.isAssignableFrom(obj.javaClass)) {
                    val iMonitor = obj as IMonitor
                    callback.onFailed(
                        ConvertException("Convert failed , target type is" + modelType!!.typeName + " but jsonStr is " + responseJsonStr),
                        iMonitor as T
                    )
                }
            }
        }
    }

    /**
     * 拼接完整的url地址（可带参数，一般给get模式用来请求）
     *
     * @param baseUrl       主机地址
     * @param interfaceName 接口名称（路径）
     * @param parameters    需要拼接进去的参数
     * @return
     */
    private fun getWholeUrl(
        baseUrl: String,
        interfaceName: String,
        parameters: Map<String, String>?
    ): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append(baseUrl).append(interfaceName).append("?")
        if (parameters != null) {
            for (key in parameters.keys) {
                stringBuilder.append(key).append("=").append(parameters[key]).append("&")
            }
            stringBuilder.deleteCharAt(stringBuilder.length - 1)
        }
        return stringBuilder.toString()
    }

    private fun getDefaultClient(config: BaseHttp?): OkHttpClient? {
        if (defaultClient == null) {
            synchronized(OkHttpClient::class.java) {
                if (defaultClient == null) {
                    val httpLoggingInterceptor =
                        HttpLoggingInterceptor(object :
                            HttpLoggingInterceptor.Logger {
                            override fun log(message: String) {
                                try {
                                    //Convert to UTF-8 encoding, otherwise garbled characters will be displayed in logcat
                                    val msgText =
                                        URLDecoder.decode(message, "utf-8")
                                    OkLogger.debug(msgText)
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        })


                    //携带一个握手证书
                    val clientCertificates =
                        HandshakeCertificates.Builder()
                            .addPlatformTrustedCertificates()
                            .build()

                    //开启日志打印
                    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                    defaultClient = OkHttpClient.Builder()
                        .sslSocketFactory(
                            clientCertificates.sslSocketFactory(),
                            clientCertificates.trustManager
                        )
                        .connectTimeout(
                            config?.getConnectTimeout()!!,
                            TimeUnit.MILLISECONDS
                        )
                        .readTimeout(
                            config?.getReadTimeout()!!,
                            TimeUnit.MILLISECONDS
                        )
                        .writeTimeout(
                            config?.getWriteTimeout()!!,
                            TimeUnit.MILLISECONDS
                        )
                        .addInterceptor(httpLoggingInterceptor).build()
                }
            }
        }
        return defaultClient
    }

    companion object {
        private const val RESPONSE_OK = 200

        @Volatile
        var instance: OkHttpUtils? = null
            get() {
                if (field == null) {
                    synchronized(OkHttpUtils::class.java) {
                        if (field == null) {
                            field = OkHttpUtils()
                        }
                    }
                }
                return field
            }
            private set
    }

}