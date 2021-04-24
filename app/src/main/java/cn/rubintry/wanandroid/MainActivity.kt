package cn.rubintry.wanandroid

import android.view.View
import cn.gorouter.api.launcher.GoRouter
import cn.gorouter.api.utils.Callback
import cn.rubintry.lib_common.base.BaseActivity
import cn.rubintry.lib_common.base.BaseViewModel
import cn.rubintry.wanandroid.databinding.ActivityMainBinding


class MainActivity : BaseActivity() {
    private var binding: ActivityMainBinding ?= null

    override fun setLayout(): View? {
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun transparentStatusBar(): Boolean {
        return true
    }

    override fun getViewModels(): MutableList<BaseViewModel>? {
        return null
    }

    override fun initView() {
        GoRouter.getInstance().build("/login/GuideActivity").go(object : Callback{
            override fun onFail(ex: Throwable?) {

            }

            override fun onArrival() {
                finish()
            }

        })
    }

    override fun requestData() {

    }

}