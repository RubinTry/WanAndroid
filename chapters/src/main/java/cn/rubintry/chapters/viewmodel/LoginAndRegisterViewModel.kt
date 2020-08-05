package cn.rubintry.chapters.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.rubintry.common.model.BaseModel
import cn.rubintry.common.model.LoginModel
import cn.rubintry.common.model.RegisterModel
import cn.rubintry.common.utils.http.OkHttpUtils
import cn.rubintry.common.utils.http.ResponseCallback

/**
 * @author logcat
 */
class LoginAndRegisterViewModel : ViewModel() {
    private var userData: MutableLiveData<BaseModel<LoginModel>>? = null
    private var registerData: MutableLiveData<BaseModel<RegisterModel>>? = null

    /**
     * 监听登录数据
     * @param userName
     * @param password
     * @return
     */
    fun login(
        userName: String,
        password: String
    ): MutableLiveData<BaseModel<LoginModel>> {
        if (userData == null) {
            userData = MutableLiveData()
        }
        requestLogin(userName, password)
        return userData as MutableLiveData<BaseModel<LoginModel>>
    }

    /**
     * 发起登录请求
     * @param userName
     * @param password
     */
    private fun requestLogin(userName: String, password: String) {
        OkHttpUtils.instance
            ?.post("/user/login")
            ?.addParameters("username", userName)
            ?.addParameters("password", password)
            ?.request(object : ResponseCallback<BaseModel<LoginModel>>(){
                override fun onSuccess(t: BaseModel<LoginModel>) {
                    userData?.postValue(t)
                }

                override fun onFailed(e: Exception?, t: BaseModel<LoginModel>?) {
                    userData?.postValue(t)
                }
            })
    }

    /**
     * 监听注册账号
     * @param username
     * @param password
     * @param rePassword
     * @return
     */
    fun register(
        username: String,
        password: String,
        rePassword: String
    ): MutableLiveData<BaseModel<RegisterModel>> {
        if (registerData == null) {
            registerData = MutableLiveData()
        }
        requestRegister(username, password, rePassword)
        return registerData as MutableLiveData<BaseModel<RegisterModel>>
    }

    /**
     * 发起注册请求
     * @param username
     * @param password
     * @param rePassword
     */
    private fun requestRegister(
        username: String,
        password: String,
        rePassword: String
    ) {
        OkHttpUtils.instance
            ?.post("/user/register")
            ?.addParameters("username", username)
            ?.addParameters("password", password)
            ?.addParameters("repassword", rePassword)
            ?.request(object : ResponseCallback<BaseModel<RegisterModel>>(){
                override fun onSuccess(t: BaseModel<RegisterModel>) {
                    registerData?.postValue(t)
                }

                override fun onFailed(e: Exception?, t: BaseModel<RegisterModel>?) {
                    registerData?.postValue(t)
                }

            })
    }
}