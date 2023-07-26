package com.xxx.ware_house.utils

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 *
 */
object GsonUtils {
    private val gsonObject: Gson
        get() = GsonBuilder().serializeNulls().create()

    /**
     * 对象转字符串
     *
     * @param object
     * @param <T>
     * @return
    </T> */
    @JvmStatic
    fun <T : Any?> ser(`object`: T): String {
        val gson = gsonObject
        return gson.toJson(`object`)
    }

    /**
     * 字符串转对象
     *
     * @param object
     * @param tClass
     * @param <T>
     * @return
    </T> */
    @JvmStatic
    fun <T : Any?> deser(`object`: String?, tClass: Class<T>?): T? {
        val gsonObject = gsonObject
        var result: T? = null
        try {
            result = gsonObject.fromJson(`object`, tClass)
        } catch (e: Exception) {
            result = null
            e.printStackTrace()
        }
        return result
    }

    fun <T : Any?> deserBeQuiet(`object`: String?, tClass: Class<T>?): T? {
        val gson = gsonObject
        val result: T?
        result = try {
            gson.fromJson(`object`, tClass)
        } catch (e: Exception) {
            null
        }
        return result
    }

    fun <T> Json2Result(tClass: Class<T>, s: String?): T? {
        var result: T?
        try {
            result = Gson().fromJson(s, tClass)
            Log.d("$tClass-----Json Mes", s!!)
        } catch (e: Exception) {
            result = null
            Log.e("$tClass-----Json Error", s!!)
        }
        return result
    }
}