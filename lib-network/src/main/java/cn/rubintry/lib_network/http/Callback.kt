package cn.rubintry.lib_network.http

interface Callback<T> {
    fun result(t: T)
}