package cn.rubintry.lib_network.http

import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataCallAdapter<T>(mResponseType : Type, isBaseModel: Boolean) : CallAdapter<T, LiveData<T>> {

    private var mResponseType: Type? = null
    private var isBaseModel: Boolean = false

    init {
        this.mResponseType = mResponseType
        this.isBaseModel = isBaseModel
    }

    override fun responseType(): Type? {
        return mResponseType
    }

    override fun adapt(call: Call<T>): LiveData<T> {
        return ResponseData(call , isBaseModel)
    }


    inner class ResponseData<T>(call: Call<T>, isBaseModel: Boolean) : LiveData<T>() {

        private val stared = AtomicBoolean(false)
        private var call: Call<T>? = null
        private var isBaseModel = false

        init {
            this.call = call
            this.isBaseModel = isBaseModel
        }


        override fun onActive() {
            super.onActive()
            //确保执行一次
            //确保执行一次
            val b = stared.compareAndSet(false, true)
            if (b) {
                call?.enqueue(object : Callback<T> {
                    override fun onResponse(call: Call<T>, response: Response<T>) {
                        val body = response.body()
                        postValue(body)
                    }

                    override fun onFailure(call: Call<T>, t: Throwable) {
                        if (isBaseModel) {
                            postValue(LiveBaseModel(ErrorCode.CODE_ERROR , t.message) as T)
                        } else {
                            postValue(t.message as T?)
                        }
                    }


                })
            }
        }
    }
}