package cn.rubintry.lib_network.http

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type
import retrofit2.converter.gson.GsonConverterFactory


/**
 *
 * @constructor 改造后的json解析器
 * Copy from [GsonConverterFactory]
 *
 * @param gson
 */
class JsonConvertFactory(gson: Gson) : Converter.Factory() {

    companion object{
        /**
         * Create an instance using a default [Gson] instance for conversion. Encoding to JSON and
         * decoding from JSON (when no charset is specified by a header) will use UTF-8.
         */
        fun create(): JsonConvertFactory {
            //使用GsonBuilder().serializeNulls().disableHtmlEscaping().create()而非直接new一个Gson的目的：可在logcat中只打印body里的关键部分，避免打印所有log造成混乱
            return create(GsonBuilder().serializeNulls().disableHtmlEscaping().create())
        }

        /**
         * Create an instance using `gson` for conversion. Encoding to JSON and decoding from JSON
         * (when no charset is specified by a header) will use UTF-8.
         */
        // Guarding public API nullability.
        fun create(gson: Gson?): JsonConvertFactory {
            if (gson == null) throw NullPointerException("gson == null")
            return JsonConvertFactory(gson)
        }
    }

    private var gson: Gson? = null

    init {
        this.gson = gson
    }

    override fun responseBodyConverter(
        type: Type?, annotations: Array<Annotation?>?, retrofit: Retrofit?
    ): Converter<ResponseBody , *> {
        val adapter = gson?.getAdapter(TypeToken.get(type))
        return JsonResponseConverter(gson!!, adapter)
    }

    override fun requestBodyConverter(
        type: Type?,
        parameterAnnotations: Array<Annotation?>?,
        methodAnnotations: Array<Annotation?>?,
        retrofit: Retrofit?
    ): Converter<* , RequestBody> {
        val adapter : TypeAdapter<*> = gson?.getAdapter(TypeToken.get(type)) as TypeAdapter<*>
        return JsonRequestConverter(gson!!, adapter)
    }

}