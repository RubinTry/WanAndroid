package cn.rubintry.module_login

import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import cn.gorouter.annotation.Route
import cn.rubintry.lib_common.base.BaseActivity
import cn.rubintry.lib_common.base.BaseViewModel
import cn.rubintry.module_login.databinding.ActivityLoginBinding
import cn.rubintry.module_login.http.model.LoginModel
import cn.rubintry.module_login.http.viewmodel.LoginViewModel



@Route(url = "login/LoginActivity")
class LoginActivity : BaseActivity() {
    private var binding: ActivityLoginBinding? = null
    private val loginViewModel: LoginViewModel by viewModels<LoginViewModel>()

    override fun setLayout(): View? {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun getViewModels(): MutableList<BaseViewModel> {
        return mutableListOf(loginViewModel)
    }


    override fun initView() {
    }

    override fun requestData() {

        val loginObserver = Observer<LoginModel> { loginData ->
            binding?.tvLoginData?.text = loginData.nickname
        }

        loginViewModel.setParams("rubintry", "123456")
        loginViewModel.login.observe(this, loginObserver)
    }

}