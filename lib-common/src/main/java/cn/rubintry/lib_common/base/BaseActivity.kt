package cn.rubintry.lib_common.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel

abstract class BaseActivity : AppCompatActivity() {

    private var viewModelList = mutableListOf<BaseViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayout())
    }

    override fun onStart() {
        super.onStart()
        viewModelList = getViewModels()
        for (baseViewModel in viewModelList) {
            baseViewModel.lifecycleOwner = this
        }
        initView()
        requestData()
    }

    protected abstract fun setLayout(): View?

    protected abstract fun getViewModels(): MutableList<BaseViewModel>

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