package cn.rubintry.module_login.activity

import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import cn.gorouter.annotation.Route
import cn.rubintry.lib_common.base.BaseActivity
import cn.rubintry.lib_common.base.BaseViewModel
import cn.rubintry.module_login.databinding.ActivityLoginBinding
import cn.rubintry.module_login.http.model.LoginModel
import cn.rubintry.module_login.http.viewmodel.LoginViewModel
import cn.rubintry.module_login.setGradientColor
import com.blankj.utilcode.util.GsonUtils


@Route(url = "/login/LoginActivity")
class LoginActivity : BaseActivity() {
    private var binding: ActivityLoginBinding? = null
    private val loginViewModel: LoginViewModel by viewModels<LoginViewModel>()

    override fun setLayout(): View? {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun transparentStatusBar(): Boolean {
        return true
    }

    override fun getViewModels(): MutableList<BaseViewModel> {
        return mutableListOf(loginViewModel)
    }


    override fun initView() {
    }

    override fun requestData() {


        val colors = intArrayOf(
            Color.parseColor("#0F8DF2"),
            Color.parseColor("#1D8AF3"),
            Color.parseColor("#01C6FB")
        )
        val positions = floatArrayOf(0f, 0.5f, 1.0f)
        binding?.tvLabelAndroid?.setGradientColor(colors, positions)


        loginViewModel.setParams("rubintry" , "123456").login.observe(this, { data ->
            Log.d("TAG", "data: ${GsonUtils.toJson(data)}")
        })
    }

}