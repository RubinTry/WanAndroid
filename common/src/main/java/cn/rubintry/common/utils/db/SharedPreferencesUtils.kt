package cn.rubintry.common.utils.db

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import java.lang.Exception
import java.lang.StringBuilder

/**
 * @author logcat
 */
class SharedPreferencesUtils {
    private var context: Context? = null

    private var delimiter : String = "-cn.rubintry.sp-"

    private var sharedPreferences: SharedPreferences? = null
    private val lock = Any()
    fun initialize(context: Context, name: String?, mode: Int) {
        this.context = context.applicationContext
        sharedPreferences = context.getSharedPreferences(name, mode)
    }

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
        sharedPreferences?.edit { putString(key, value) }
    }

    fun put(key: String, value: Int) {
        sharedPreferences?.edit { putInt(key, value) }
    }


    fun put(key: String, value: Long) {
        sharedPreferences?.edit { putLong(key, value) }
    }


    fun putObject(key: String , value : Any){
        val jsonSb = StringBuilder()
        jsonSb.append(Gson().toJson(value)).append(delimiter).append(value.javaClass.name)
        sharedPreferences?.edit{ putString(key , jsonSb.toString())}
    }



    fun putStringSet(key: String, value: Set<String>) {
        sharedPreferences?.edit { putStringSet(key, value) }
    }


    fun getString(key: String): String? {
        return sharedPreferences?.getString(key, "empty")
    }


    fun getInt(key: String): Int? {
        return sharedPreferences?.getInt(key, 0)
    }


    fun getLong(key: String): Long? {
        return sharedPreferences?.getLong(key, 0L);
    }


    fun getObject(key: String) : Any? {
        val content : String? = getString(key)
        try {
            val jsonStr = content?.split(delimiter)?.get(0)
            val typeName = content?.split(delimiter)?.get(1)
            val clazz : Class<*> = Class.forName(typeName)
            val obj = Gson().fromJson(jsonStr , clazz)
            return obj
        }catch (ex : Exception){
            ex.printStackTrace()
        }



        return null
    }


    fun clear(){
        sharedPreferences?.edit()?.clear()
    }


    fun contains(key : String): Boolean? {
        return sharedPreferences?.contains(key)
    }

    fun remove(key : String) {
        sharedPreferences?.edit()?.remove(key)
    }
}