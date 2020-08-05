package cn.rubintry.common.utils.http

/**
 * @author logcat
 */
class ServerException(message: String? , private val code : Int) : RuntimeException(message) {


    fun getCode() : Int{
        return code;
    }

}