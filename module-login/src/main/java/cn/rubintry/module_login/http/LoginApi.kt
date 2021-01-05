package cn.rubintry.module_login.http

import androidx.lifecycle.LiveData
import cn.rubintry.lib_common.base.BaseModel
import cn.rubintry.lib_network.http.NetWorkService
import cn.rubintry.module_login.http.model.LoginModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApi : NetWorkService {

    @FormUrlEncoded
    @POST("/user/login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): LiveData<BaseModel<LoginModel>>
}