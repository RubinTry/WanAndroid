package cn.rubintry.lib_common.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.BarUtils

abstract class BaseActivity : AppCompatActivity() {

    private var viewModelList = mutableListOf<BaseViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayout())
        if(transparentStatusBar()){
            BarUtils.transparentStatusBar(this)
        }

        BarUtils.setStatusBarLightMode(this , true)

        getViewModels()?.let { viewModelList.addAll(it) }
        for (baseViewModel in viewModelList) {
            baseViewModel.lifecycleOwner = this
        }
        initView()
        requestData()
    }


    protected abstract fun setLayout(): View?

    protected abstract fun transparentStatusBar(): Boolean

    protected abstract fun getViewModels(): MutableList<BaseViewModel>?

    protected abstract fun initView()

    protected abstract fun requestData()

    override fun onStop() {
        super.onStop()
        for (baseViewModel in viewModelList) {
            baseViewModel.lifecycleOwner = null
        }
        viewModelList.clear()
    }
}