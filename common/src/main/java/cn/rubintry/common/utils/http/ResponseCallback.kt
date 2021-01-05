package cn.rubintry.common.utils.http

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @author logcat
 */
abstract class ResponseCallback<T> {
//    private val TAG = this.javaClass.simpleName

    /**
     * 请求成功
     *
     * @param t
     */
    abstract fun onSuccess(t: T)

    /**
     * 请求失败
     *
     * @param e
     */
    abstract fun onFailed(e: Exception?, t: T?)

    /**
     * 反射获得泛型对应的type
     *
     * @return
     */
    val modelType: ParameterizedType?
        get() {
            var clazz: Class<*> = javaClass
            while (clazz != Any::class.java) {
                val t = clazz.genericSuperclass
                if (t is ParameterizedType) {
                    val args =
                        t.actualTypeArguments
                    return args[0] as ParameterizedType
                }
                clazz = clazz.superclass
            }
            return null
        }
}