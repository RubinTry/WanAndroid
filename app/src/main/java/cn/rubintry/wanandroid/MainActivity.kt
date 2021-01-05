package cn.rubintry.wanandroid

<<<<<<< HEAD
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
=======
import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import butterknife.BindView
import butterknife.ButterKnife
import cn.gorouter.annotation.Route
import cn.gorouter.api.launcher.GoRouter
import cn.gorouter.api.monitor.ActivityMonitor
import cn.gorouter.api.monitor.FragmentMonitor
import cn.rubintry.common.base.BaseActivity
import cn.rubintry.common.utils.ToastUtils

@Route(url = "app/MainActivity")
class MainActivity : BaseActivity() {


    @BindView(R.id.flPageContainer)
    lateinit var flPageContainer: FrameLayout

    private val REQUEST_PERMISSION_CODE: Int = 10001

    private val TAG = this.javaClass.simpleName

    override fun attachedLayoutRes(): Int? {
        return R.layout.activity_main
    }

    override fun initViews() {
        ButterKnife.bind(this)

        var containerBundle = Bundle()
        containerBundle.putInt("containerId", flPageContainer.id)

        //跳出加载页
        GoRouter.getInstance().build("app/SplashFragment", containerBundle)
            .setFragmentContainer(flPageContainer.id)
            .go()
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


    }


    override fun lightMode(): Boolean {
        return true
    }


    override fun onBackPressed() {
        if (FragmentMonitor.instance?.canExit()!!) {
            //强制杀死当前进程
            ActivityMonitor.instance?.exit()
        } else {
            FragmentMonitor.instance?.finish()
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


    override fun onDestroy() {
        super.onDestroy()
    }

>>>>>>> b86d385c9aeacc8d38ae771c802539495ef6cdab
}