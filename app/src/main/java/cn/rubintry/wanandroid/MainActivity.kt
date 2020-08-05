package cn.rubintry.wanandroid

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import butterknife.BindView
import butterknife.ButterKnife
import cn.gorouter.annotation.Route
import cn.gorouter.api.launcher.GoRouter
import cn.rubintry.common.ActivityMonitor
import cn.rubintry.common.FragmentMonitor
import cn.rubintry.common.base.BaseActivity
import cn.rubintry.common.model.BaseModel
import cn.rubintry.common.model.CacheConstants
import cn.rubintry.common.model.LoginModel
import cn.rubintry.common.utils.ClassUtils
import cn.rubintry.common.utils.ToastUtils
import cn.rubintry.common.utils.db.SharedPreferencesUtils
import cn.rubintry.common.utils.http.OkHttpUtils
import cn.rubintry.common.utils.http.ResponseCallback

@Route(url = "app/MainActivity")
class MainActivity : BaseActivity() {


    @BindView(R.id.flPageContainer)
    lateinit var flPageContainer: FrameLayout

    private val REQUEST_PERMISSION_CODE: Int = 10001

    override fun attachedLayoutRes(): Int? {
        return R.layout.activity_main
    }

    override fun initViews() {
        ButterKnife.bind(this)

        //跳出加载页
        jumpToFragment(SplashFragment(), flPageContainer)

        val permissions = arrayOf(Manifest.permission.INTERNET)


        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(permissions, REQUEST_PERMISSION_CODE)
                    break
                }
            }
        }

        //权限请求成功,?.代表安全调用，防止内部出现NPE，涉及知识点（kotlin的空安全性）
        val cacheLoginData = SharedPreferencesUtils.instance?.getObject(
            CacheConstants.LOGIN_INFO
        )
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            if (cacheLoginData != null) {
                requestToLogin(cacheLoginData);
            } else {
                val loginFragment : Fragment = GoRouter.getInstance().build("chapters/LoginFragment").fragmentInstance
                jumpToFragment(loginFragment , flPageContainer)
                finishFragment(SplashFragment::class.java)
            }
        }, 1000)
    }


    override fun lightMode(): Boolean {
        return true
    }

    private fun requestToLogin(cacheLoginData: Any) {
        //反射获取其他模块中存下来的登录数据里的字段值
        val userName = ClassUtils.getPropertyValue(cacheLoginData, "username") as String
        val password = ClassUtils.getPropertyValue(cacheLoginData, "password") as String


        OkHttpUtils.instance
            ?.post("/user/login")
            ?.addParameters("username", userName)
            ?.addParameters("password", password)
            ?.request(object : ResponseCallback<BaseModel<LoginModel?>?>() {
                override fun onSuccess(t: BaseModel<LoginModel?>?) {

                    val fragment : Fragment? = GoRouter.getInstance().build("chapters/HomeFragment").fragmentInstance
                    jumpToFragment(fragment, flPageContainer);
                    if(fragment != null){
                        finishFragment(SplashFragment::class.java)
                    }
                }

                override fun onFailed(e: Exception?, t: BaseModel<LoginModel?>?) {
                    ToastUtils.showShort("自动登录失败：" + e?.message)
                    val loginFragment : Fragment = GoRouter.getInstance().build("chapters/LoginFragment").fragmentInstance
                    jumpToFragment(loginFragment , flPageContainer);
                    finishFragment(SplashFragment::class.java)
                }
            })

    }


    override fun onBackPressed() {
        if (FragmentMonitor.instance?.getFragmentCount() == 1) {
            FragmentMonitor?.instance?.clear()
            super.onBackPressed()
            //强制杀死当前进程
            ActivityMonitor.instance?.exit()
        } else {
            FragmentMonitor.instance?.finishLast()
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_PERMISSION_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.

                } else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                    //权限请求失败
                    ToastUtils.showShort("您拒绝了权限，可能会导致部分功能无法正常使用")
                }
                return
            }
        }
    }
}