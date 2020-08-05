package cn.rubintry.common.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import cn.rubintry.common.ActivityMonitor
import cn.rubintry.common.FragmentMonitor

abstract class BaseActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityMonitor.instance?.add(this)
        if(attachedLayoutRes() != null){
            val resId = attachedLayoutRes()
            setContentView(resId!!)
        }
        initViews()




        transparentStatusBar(this.window)

        if (lightMode()) {
            setStatusBarLightMode(this.window, true)
        } else {
            setStatusBarLightMode(this.window, false)
        }


    }



    /**
     * 设置布局文件
     */
    protected abstract fun attachedLayoutRes(): Int?


    protected abstract fun initViews()

    /**
     * 状态栏是否为明亮模式
     * @return
     */
    protected abstract fun lightMode(): Boolean

    protected fun jumpToFragment(fragment: Fragment? , container : View){
        if(fragment == null){
            return
        }
        FragmentMonitor.instance?.jump(fragment ,  container)
    }


    protected fun finishFragment(aClass: Class<out Fragment?>?){
        if(FragmentMonitor.instance?.contains(aClass)!!){
            FragmentMonitor.instance?.finish(aClass)
        }

    }

    private fun transparentStatusBar(window: Window) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            val option =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            val vis = window.decorView.systemUiVisibility
            window.decorView.systemUiVisibility = option or vis
            window.statusBarColor = Color.TRANSPARENT
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }


    fun setStatusBarLightMode(window: Window, isLightMode: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decorView = window.decorView
            var vis = decorView.systemUiVisibility
            vis = if (isLightMode) {
                vis or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                vis and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
            decorView.systemUiVisibility = vis
        }
    }

    override fun finish() {
        ActivityMonitor.instance?.remove(this)
        super.finish()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}