package cn.rubintry.common.utils.http

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType

/**
 * @author logcat
 */
object MediaTypePool {
    val DEFAULT: MediaType = "application/json; charset=utf-8".toMediaType()
}