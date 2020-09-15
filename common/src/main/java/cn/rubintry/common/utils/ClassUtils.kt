package cn.rubintry.common.utils

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @author logcat
 * 属性值工具类
 */
object ClassUtils {
    /**
     * 取出object对象中属性名为propertyName的属性值
     *
     * @param obj
     * @param propertyName
     * @return
     */
    @Throws(Exception::class)
    fun getPropertyValue(obj: Any, propertyName: String): Any? {
        val fields = obj.javaClass.declaredFields
        for (field in fields) {
            field.isAccessible = true
            if (field.name == propertyName) {
                return field[obj]
            }
        }
        return null
    }

    fun getNewInstance(className: String?): Any? {
        if (className == null) {
            return null
        }
        try {
            val aClass = Class.forName(className)
            return aClass.newInstance()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}