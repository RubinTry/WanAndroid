package cn.rubintry.chapters.fragment

import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import butterknife.BindView
import butterknife.OnClick
import cn.gorouter.annotation.Route
import cn.gorouter.api.launcher.GoRouter
import cn.gorouter.api.monitor.FragmentMonitor
import cn.rubintry.chapters.R
import cn.rubintry.chapters.R2
import cn.rubintry.chapters.viewmodel.LoginAndRegisterViewModel
import cn.rubintry.common.Const
import cn.rubintry.common.base.BaseFragment
import cn.rubintry.common.model.CacheConstants
import cn.rubintry.common.utils.ClassUtils
import cn.rubintry.common.utils.ToastUtils
import cn.rubintry.common.utils.db.SharedPreferencesUtils
import java.lang.Exception


@Route(url = "chapters/LoginFragment")
class LoginFragment : BaseFragment(){

    @BindView(R2.id.edtUserName)
    lateinit var edtUserName: EditText

    @BindView(R2.id.edtPassword)
    lateinit var edtPassword: EditText

    @BindView(R2.id.tvBtnLogin)
    lateinit var tvBtnLogin: TextView

    @BindView(R2.id.tvGotoRegister)
    lateinit var tvGotoRegister: TextView

    private var containerId : Int ?= null

    private val loginAndRegisterViewModel: LoginAndRegisterViewModel by viewModels()

    override fun attachedLayoutRes(): Int {
        return R.layout.fragment_login
    }

    override fun processor(){
        val cacheLoginData = SharedPreferencesUtils.instance?.getObject(
            CacheConstants.LOGIN_INFO
        )

        containerId = arguments?.getInt(Const.CONTAINER_ID);

        if(cacheLoginData != null){
            try {
                val userName = ClassUtils.getPropertyValue(cacheLoginData, "username") as String
                edtUserName.setText(userName)
            }catch (ex : Exception){
                ex.printStackTrace()
            }
        }
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
                GoRouter.getInstance().build("chapters/RegisterFragment").setFragmentContainer(containerId!!).go()
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
                    result.data?.password = password
                    SharedPreferencesUtils.instance?.putObject(
                        CacheConstants.LOGIN_INFO,
                        result?.data!!
                    )
                    if(showSuccessTips){
                        ToastUtils.showShort("登录成功")
                    }

                    FragmentMonitor.instance?.finish()

                    GoRouter.getInstance()
                        .build("chapters/HomeFragment")
                        .setFragmentContainer(containerId!!).go()

                } else {
                    ToastUtils.showShort("登录失败" + result.errorMsg)

                }
            })
    }


}