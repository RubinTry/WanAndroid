package cn.rubintry.module_login.activity

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import cn.gorouter.annotation.Route
import cn.gorouter.api.launcher.GoRouter
import cn.gorouter.api.utils.Callback
import cn.rubintry.lib_common.base.BaseActivity
import cn.rubintry.lib_common.base.BaseViewModel
import cn.rubintry.module_login.R
import cn.rubintry.module_login.databinding.ActivityGuideBinding
import kotlinx.coroutines.*


@Route(url = "/login/GuideActivity")
class GuideActivity : BaseActivity() {
    private var timerJob: Job? = null
    private var binding: ActivityGuideBinding? = null
    private var second = 5

    override fun setLayout(): View? {
        binding = ActivityGuideBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun transparentStatusBar(): Boolean {
        return true
    }


    override fun getViewModels(): MutableList<BaseViewModel>? {
        return mutableListOf<BaseViewModel>()
    }

    override fun initView() {
        binding?.tvGuide1?.animation = AnimationUtils.makeInAnimation(this, false)
        binding?.tvGuide2?.animation = AnimationUtils.makeInAnimation(this, true)
        binding?.tvGuide1?.visibility = View.VISIBLE
        binding?.tvGuide2?.visibility = View.VISIBLE

        binding?.tvSkip?.setOnClickListener {
            goToLogin()
        }


        timerJob = GlobalScope.launch {
            while(second > 0){
                binding?.tvSkip?.apply {
                    post {
                        text = "跳过 ${second}s"
                    }
                }
                second --
                delay(1000)
            }
            goToLogin()
        }


    }

    private fun goToLogin() {
        GoRouter.getInstance().build("/login/LoginActivity").go(object : Callback {
            override fun onFail(ex: Throwable?) {

            }

            override fun onArrival() {
                finish()
            }

        })
    }

    override fun requestData() {

    }

    override fun onDestroy() {
        second = 0
        timerJob?.cancel(CancellationException("Cancel failed!!"))
        super.onDestroy()
    }
}