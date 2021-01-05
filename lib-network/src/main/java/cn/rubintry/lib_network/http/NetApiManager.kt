package cn.rubintry.lib_network.http

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import java.lang.Exception
import java.lang.IllegalArgumentException


/**
 * 服务器接口调用工具类，使用的所有api均需继承自[NetWorkService]接口
 *
 */
class NetApiManager {
    private var callAdapterType: CallAdapterType? = null
    private val TAG = this.javaClass.simpleName
    private var defaultRetrofit: Retrofit? = null
    private val apiMap = LinkedHashMap<Class<*>, Any>()
    private val urlMap = LinkedHashMap<String, Any>()
    private var callAdapterFactory: CallAdapter.Factory? = null
    private val builder = OkHttpClient.Builder()

    fun init(baseUrl: String, debugable: Boolean) {
        init(baseUrl, debugable, null)
    }

    fun init(baseUrl: String, debugable: Boolean, callAdapterType: CallAdapterType?) {
        this.callAdapterType = callAdapterType
        if (debugable) {
            val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLogger())
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(httpLoggingInterceptor)
        }

        if (callAdapterType == null) {
            callAdapterFactory = LiveDataCallAdapterFactory.create()
        } else if (callAdapterType == CallAdapterType.RxJava3) {
            callAdapterFactory = RxJava3CallAdapterFactory.create()
        } else if (callAdapterType == CallAdapterType.LiveData) {
            callAdapterFactory = LiveDataCallAdapterFactory.create()
        }


        defaultRetrofit = Retrofit.Builder()
            .client(builder.build())
            .baseUrl(baseUrl)
            .addCallAdapterFactory(callAdapterFactory)
            .addConverterFactory(JsonConvertFactory.create())
            .build()
    }

    fun setUrl(baseUrl: String): NetApiManager {
        return setUrl(baseUrl, callAdapterType)
    }

    fun setUrl(baseUrl: String, callAdapterType: CallAdapterType?): NetApiManager {
        defaultRetrofit = null
        this.callAdapterType = callAdapterType
        if (callAdapterType == null) {
            callAdapterFactory = LiveDataCallAdapterFactory.create()
        } else if (callAdapterType == CallAdapterType.RxJava3) {
            callAdapterFactory = RxJava3CallAdapterFactory.create()
        } else if (callAdapterType == CallAdapterType.LiveData) {
            callAdapterFactory = LiveDataCallAdapterFactory.create()
        }

        defaultRetrofit = Retrofit.Builder()
            .client(builder.build())
            .baseUrl(baseUrl)
            .addCallAdapterFactory(callAdapterFactory)
            .addConverterFactory(JsonConvertFactory.create())
            .build()
        return this
    }


    companion object {
        @Volatile
        var default: NetApiManager? = null
            get() {
                if (field == null) {
                    synchronized(NetApiManager::class.java) {
                        if (field == null) {
                            field = NetApiManager()
                        }
                    }
                }
                return field
            }
    }

    /**
     * 获取api
     * Class的泛型必须为T， 以确保外部调用时可自动将返回值转为对应的类型
     * @param T
     * @param apiClazz
     * @return
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> getApi(apiClazz: Class<T>): T? {
        if(!apiClazz.isInterface){
            throw IllegalArgumentException("API declarations must be interfaces.")
        }
        if (!NetWorkService::class.java.isAssignableFrom(apiClazz)) {
            throw IllegalArgumentException("The api class need extend from NetWorkService")
        }
        return try {
            if (apiMap.containsKey(apiClazz) && urlMap.containsKey(defaultRetrofit?.baseUrl().toString())) {
                apiMap[apiClazz] as T
            } else {
                val api = defaultRetrofit?.create(apiClazz)
                if (api != null) {
                    apiMap[apiClazz] = api
                    urlMap[defaultRetrofit?.baseUrl().toString()] = api
                }
                api as T
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Api error: ", ex)
            null
        }
    }
}