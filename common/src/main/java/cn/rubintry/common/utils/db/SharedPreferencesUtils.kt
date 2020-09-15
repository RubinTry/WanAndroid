package cn.rubintry.common.utils.db

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import androidx.core.content.edit
import cn.rubintry.common.utils.Utils
import java.io.*
import java.lang.Exception
import kotlin.jvm.internal.Ref

/**
 * @author logcat
 */
class SharedPreferencesUtils {


    private var delimiter : String = "-cn.rubintry.sp-"

    private var sharedPreferences: SharedPreferences =
        Utils.applicationContext?.getSharedPreferences("WanAndroidDataSharedPreferences" , Context.MODE_PRIVATE)!!


    companion object {
        @Volatile
        var instance: SharedPreferencesUtils? = null
            get() {
                if (field == null) {
                    synchronized(SharedPreferencesUtils::class.java) {
                        if (field == null) {
                            field = SharedPreferencesUtils()
                        }
                    }
                }
                return field
            }
            private set
    }


    fun put(key: String, value: String) {
        sharedPreferences.edit { putString(key, value) }
    }

    fun put(key: String, value: Int) {
        sharedPreferences.edit { putInt(key, value) }
    }


    fun put(key: String, value: Long) {
        sharedPreferences.edit { putLong(key, value) }
    }


    fun <T> putObject(key: String , value : T){
        val objString = ByteUtils.toBase64(value)
        sharedPreferences.edit { putString(key , objString) }
    }



    fun putStringSet(key: String, value: Set<String>) {
        sharedPreferences.edit { putStringSet(key, value) }
    }


    fun getString(key: String): String? {
        return sharedPreferences.getString(key, "")
    }


    fun getInt(key: String): Int? {
        return sharedPreferences.getInt(key, 0)
    }


    fun getLong(key: String): Long? {
        return sharedPreferences.getLong(key, 0L);
    }


    fun getObject(key: String) : Any? {
        return ByteUtils.toObject(getString(key))
    }


    fun clear(){
        sharedPreferences.edit()?.clear()
    }


    fun contains(key : String): Boolean? {
        return sharedPreferences.contains(key)
    }

    fun remove(key : String) {
        sharedPreferences.edit()?.remove(key)
    }



    class ByteUtils{
        companion object{
            fun <T> toBase64(obj : T) : String{
                var bos = ByteArrayOutputStream()
                var oos = ObjectOutputStream(bos)
                oos.writeObject(obj)
                return Base64.encodeToString(bos.toByteArray() , Base64.DEFAULT)
            }


            fun toObject(base64Str : String?) : Any?{
                if(base64Str.isNullOrBlank() || base64Str.isNullOrEmpty()){
                    return null
                }
                var byteArray = Base64.decode(base64Str , Base64.DEFAULT)
                var obj = Any()
                try {
                    var bis = ByteArrayInputStream(byteArray)
                    var ois = ObjectInputStream(bis)
                    obj = ois.readObject()
                    ois.close()
                    bis.close()
                }catch (ex : Exception){
                    ex.printStackTrace()
                }
                return obj
            }
        }
    }

}