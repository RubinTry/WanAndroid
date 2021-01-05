package cn.rubintry.wanandroid

import android.view.View
import cn.gorouter.api.launcher.GoRouter
import cn.rubintry.lib_common.base.BaseActivity
import cn.rubintry.lib_common.base.BaseViewModel
import cn.rubintry.wanandroid.databinding.ActivityMainBinding


class MainActivity : BaseActivity() {
    private var binding: ActivityMainBinding ?= null

    override fun setLayout(): View? {
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun getViewModels(): MutableList<BaseViewModel>? {
        return null
    }

    override fun initView() {
        GoRouter.getInstance().build("login/LoginActivity").go()
    }

    override fun requestData() {

    }

}