package cn.rubintry.lib_network.http

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okio.Buffer
import retrofit2.Converter
import java.io.OutputStreamWriter
import java.io.Writer
import java.nio.charset.StandardCharsets.UTF_8

class JsonRequestConverter<T>(gson: Gson, typeAdapter: TypeAdapter<T>) : Converter<T, RequestBody> {
    private val MEDIA_TYPE: MediaType = "application/json; charset=UTF-8".toMediaType()
    private val TAG = this.javaClass.simpleName
    private var gson: Gson? = null
    private var typeAdapter: TypeAdapter<T>? = null

    init {
        this.gson = gson
        this.typeAdapter = typeAdapter
    }


    override fun convert(value: T): RequestBody {
        val buffer = Buffer()
        val writer: Writer =
            OutputStreamWriter(buffer.outputStream(), UTF_8)
        val jsonWriter = gson?.newJsonWriter(writer)
        typeAdapter?.write(jsonWriter, value)
        jsonWriter?.close()
        return RequestBody.create(MEDIA_TYPE, buffer.readByteString())
    }
}