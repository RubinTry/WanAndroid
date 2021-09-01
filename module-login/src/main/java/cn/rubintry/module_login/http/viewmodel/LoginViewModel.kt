package cn.rubintry.module_login.http.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import cn.rubintry.lib_common.base.BaseModel
import cn.rubintry.lib_common.base.BaseViewModel
import cn.rubintry.lib_network.http.NetApiManager
import cn.rubintry.module_login.http.LoginApi
import cn.rubintry.module_login.http.model.LoginModel
import retrofit2.Callback

class LoginViewModel() : BaseViewModel() {

    private var username: String = ""
    private var password: String= ""

    val login: MutableLiveData<LoginModel> by lazy {
        MutableLiveData<LoginModel>().also {
            doLogin()
        }
    }

    private fun doLogin() {
        NetApiManager.default?.getApi(LoginApi::class.java)?.login(username, password)?.observe(
            lifecycleOwner!!,
            { t ->
                login.value = t?.data
            })

    }

    override fun setParams(vararg params: String): LoginViewModel {
        username = params[0]
        password = params[1]
        return this
    }


}