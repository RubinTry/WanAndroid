package cn.rubintry.chapters.fragment

import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import butterknife.BindView
import butterknife.OnClick
import cn.gorouter.api.launcher.GoRouter
import cn.rubintry.chapters.R
import cn.rubintry.chapters.R2
import cn.rubintry.chapters.viewmodel.LoginAndRegisterViewModel
import cn.rubintry.common.base.BaseFragment
import cn.rubintry.common.model.CacheConstants
import cn.rubintry.common.utils.ToastUtils
import cn.rubintry.common.utils.db.SharedPreferencesUtils

class LoginFragment : BaseFragment(){

    @BindView(R2.id.edtUserName)
    lateinit var edtUserName: EditText

    @BindView(R2.id.edtPassword)
    lateinit var edtPassword: EditText

    @BindView(R2.id.tvBtnLogin)
    lateinit var tvBtnLogin: TextView

    @BindView(R2.id.tvGotoRegister)
    lateinit var tvGotoRegister: TextView

    val loginAndRegisterViewModel: LoginAndRegisterViewModel by viewModels()

    override fun attachedLayoutRes(): Int {
        return R.layout.fragment_login
    }

    override fun initViews() {

    }


    @OnClick(value = [R2.id.tvBtnLogin, R2.id.tvGotoRegister])
    fun onClick(view: View) {
        val id: Int = view.id
        when (id) {
            R.id.tvBtnLogin -> {
                if (TextUtils.isEmpty(edtUserName.text.toString())) {
                    ToastUtils.showShort("请填写手机号码")
                    return
                }

                if (TextUtils.isEmpty(edtPassword.text.toString())) {
                    ToastUtils.showShort("请输入密码")
                    return
                }


                requestLogin(edtUserName.text.toString() , edtPassword.text.toString() , true)
            }

            R.id.tvGotoRegister -> {
//                GoRouter.getInstance().build("chapters/RegisterActivity").go()
                jumpToFragment(RegisterFragment())
            }
        }
    }

    private fun requestLogin(userName : String , password : String , showSuccessTips : Boolean) {
        loginAndRegisterViewModel.login(userName, password)
            .observe(this, Observer { result ->
                if (result != null && result.errorCode == 0) {
                    if (SharedPreferencesUtils.instance?.contains(CacheConstants.LOGIN_INFO)!!) {
                        SharedPreferencesUtils.instance?.remove(CacheConstants.LOGIN_INFO)
                    }
                    result.data.password = password
                    SharedPreferencesUtils.instance?.putObject(
                        CacheConstants.LOGIN_INFO,
                        result.data
                    )
                    if(showSuccessTips){
                        ToastUtils.showShort("登录成功")
                    }
                    jumpToFragment(HomeFragment())
                    finish()

                } else {
                    ToastUtils.showShort("登录失败" + result.errorMsg)

                }
            })
    }
}