package cn.rubintry.lib_network.http

import android.util.Log
import androidx.lifecycle.LiveData
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class LiveDataCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {

        if (getRawType(returnType) != LiveData::class.java) {
            return null
        }

        //获取第一个泛型类型
        val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
        val rawType: Class<*> = getRawType(observableType)
        var isBaseModel = false
        if (rawType == LiveBaseModel::class.java) {
            isBaseModel = true
        }

        if (observableType !is ParameterizedType) {
            Log.e("TAG", "rawType = resource must be parameterized$rawType")
        }

        return LiveDataCallAdapter<String>(observableType , isBaseModel)
    }


    companion object{
        fun create(): LiveDataCallAdapterFactory{
            return LiveDataCallAdapterFactory()
        }
    }
}