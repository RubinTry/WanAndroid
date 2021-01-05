package cn.rubintry.lib_network.http

import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonToken
import okhttp3.ResponseBody
import retrofit2.Converter
import java.lang.Exception

class JsonResponseConverter<T>(gson: Gson, typeAdapter: TypeAdapter<T>?) : Converter<ResponseBody, T> {
    private val TAG = this.javaClass.simpleName
    private var gson: Gson?= null
    private var typeAdapter : TypeAdapter<T> ?= null

    init {
        this.gson = gson
        this.typeAdapter = typeAdapter
    }


    override fun convert(value: ResponseBody): T? {
        val jsonReader = gson?.newJsonReader(value.charStream())
        jsonReader?.isLenient = true
        return try {
            val result: T? = typeAdapter?.read(jsonReader)
            if (jsonReader?.peek() != JsonToken.END_DOCUMENT) {
                throw JsonIOException("JSON document was not fully consumed.")
            }
            result
        } catch (ex: Exception){
            null
        }finally {
            value.close()
        }
    }
}