package cn.rubintry.common.utils.http

/**
 * @author logcat
 * 需要实现的自己的服务端监听器
 * 一般该接口需要实现在返回时的数据模型基类中
 */
interface IMonitor {
    /**
     * 监听返回数据的错误信息
     * @return
     */
    val errMsg: String?

    /**
     * 监听返回数据的数据结构并处理
     * @return
     */
    val code: Int

    /**
     * 服务端请求通过的判断条件，注： 不仅要可以正常访问服务器，而且服务端会正常返回数据时会触发该方法
     * @return
     */
    fun success(): Boolean
}