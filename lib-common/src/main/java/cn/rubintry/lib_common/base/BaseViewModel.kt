package cn.rubintry.lib_common.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel

abstract class BaseViewModel() : ViewModel() {
    var lifecycleOwner: LifecycleOwner ?= null

    /**
     * 在使用每个viewModel发起请求前，都调用一次这个方法来设置请求时需要的参数
     *
     * @param params
     */
    abstract fun setParams(vararg params: String)
}