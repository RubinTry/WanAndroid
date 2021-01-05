package cn.rubintry.chapters.fragment

import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import butterknife.BindView
import butterknife.OnClick
import cn.gorouter.annotation.Route
import cn.gorouter.api.monitor.FragmentMonitor
import cn.rubintry.chapters.R
import cn.rubintry.chapters.R2
import cn.rubintry.chapters.viewmodel.LoginAndRegisterViewModel
import cn.rubintry.common.base.BaseFragment
import cn.rubintry.common.utils.ToastUtils


@Route(url = "chapters/RegisterFragment")
class RegisterFragment : BaseFragment() {
    @BindView(R2.id.tvBtnRegister)
    lateinit var tvBtnRegister : TextView
    @BindView(R2.id.edtUserName)
    lateinit var edtUserName : EditText
    @BindView(R2.id.edtPassword)
    lateinit var edtPassword : EditText
    @BindView(R2.id.edtRePassword)
    lateinit var edtRePassword : EditText


    override fun attachedLayoutRes(): Int {
        return R.layout.fragment_register
    }

    override fun processor() {

    }



    @OnClick(value = [R2.id.tvBtnRegister])
    fun onClick(view : View){
        when(view.id){
            R.id.tvBtnRegister -> {
                if(TextUtils.isEmpty(edtUserName.text.toString())){
                    ToastUtils.showShort("请填写手机号码")
                    return
                }

                if(TextUtils.isEmpty(edtPassword.text.toString())){
                    ToastUtils.showShort("请输入密码")
                    return
                }

                if(TextUtils.isEmpty(edtRePassword.text.toString())){
                    ToastUtils.showShort("请输入密码")
                    return
                }

                if(!TextUtils.equals(edtPassword.text.toString() , edtRePassword.text.toString())){
                    ToastUtils.showShort("两次输入不一致")
                    return
                }

                val loginAndRegisterModel : LoginAndRegisterViewModel by viewModels()
                loginAndRegisterModel.register(edtUserName.text.toString() , edtPassword.text.toString() , edtRePassword.text.toString())
                    .observe(this , Observer { result ->
                        if(result != null && result.errorCode == 0){
                            ToastUtils.showShort("注册成功")
//                            finish()
                            FragmentMonitor.instance?.finish()
                        }else{
                            ToastUtils.showShort("注册失败 : " + result.errorMsg)
                        }
                    })

            }
        }
    }
}