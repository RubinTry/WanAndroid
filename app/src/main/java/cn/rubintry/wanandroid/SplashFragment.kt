package cn.rubintry.wanandroid

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import cn.gorouter.annotation.Route
import cn.gorouter.api.launcher.GoRouter
import cn.gorouter.api.monitor.FragmentMonitor
import cn.rubintry.common.Const
import cn.rubintry.common.base.BaseFragment
import cn.rubintry.common.model.BaseModel
import cn.rubintry.common.model.CacheConstants
import cn.rubintry.common.model.LoginModel
import cn.rubintry.common.utils.ClassUtils
import cn.rubintry.common.utils.ToastUtils
import cn.rubintry.common.utils.db.SharedPreferencesUtils
import cn.rubintry.common.utils.http.OkHttpUtils
import cn.rubintry.common.utils.http.ResponseCallback


@Route(url = "app/SplashFragment")
open class SplashFragment : BaseFragment() {

    var containerId : Int ?= null
    override fun attachedLayoutRes(): Int {
        return R.layout.fragment_splash
    }

    override fun processor() {
        //权限请求成功,?.代表安全调用，防止内部出现NPE，涉及知识点（kotlin的空安全性）
        val cacheLoginData = SharedPreferencesUtils.instance?.getObject(
            CacheConstants.LOGIN_INFO
        )
        containerId = arguments?.getInt("containerId")
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            if (cacheLoginData != null) {
                requestToLogin(cacheLoginData);
            } else {
                FragmentMonitor.instance?.finish()
                var bundle  = Bundle()
                bundle.putInt(Const.CONTAINER_ID , containerId!!)
                GoRouter.getInstance()
                    .build("chapters/LoginFragment" , bundle)
                    .setFragmentContainer(containerId!!).go()

            }

        }, 2000)
    }


    private fun requestToLogin(cacheLoginData: Any) {

        try {
            //反射获取其他模块中存下来的登录数据里的字段值
            val userName = ClassUtils.getPropertyValue(cacheLoginData, "username") as String?
            val password = ClassUtils.getPropertyValue(cacheLoginData, "password") as String?
            OkHttpUtils.instance
                ?.post("/user/login")
                ?.addParameters("username", userName)
                ?.addParameters("password", password)
                ?.request(object : ResponseCallback<BaseModel<LoginModel?>?>() {
                    override fun onSuccess(t: BaseModel<LoginModel?>?) {
                        FragmentMonitor.instance?.finish()

                        var bundle = Bundle()
                        bundle.putInt(Const.CONTAINER_ID , containerId!!)
                        GoRouter.getInstance()
                            .build("chapters/HomeFragment" , bundle)
                            .setFragmentContainer(containerId!!)
                            .go()
                    }

                    override fun onFailed(e: Exception?, t: BaseModel<LoginModel?>?) {
                        ToastUtils.showShort("自动登录失败：" + e?.message)

                        FragmentMonitor.instance?.finish()

                        var bundle = Bundle()
                        bundle.putInt(Const.CONTAINER_ID , containerId!!)
                        GoRouter.getInstance()
                            .build("chapters/LoginFragment" , bundle)
                            .setFragmentContainer(containerId!!)
                            .go()
                    }
                })
        }catch (ex : java.lang.Exception){
            ex.printStackTrace()
            FragmentMonitor.instance?.finish()
            var bundle : Bundle = Bundle()
            bundle.putInt(Const.CONTAINER_ID , containerId!!)
            GoRouter.getInstance().build("chapters/LoginFragment" , bundle).setFragmentContainer(containerId!!)
                .go()
        }




    }
}