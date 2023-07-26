package com.xxx.ware_house.net

import android.os.Environment
import android.text.TextUtils
import com.xxx.ware_house.api.RetrofitApiService
import com.xxx.ware_house.common.SystemCommon
import com.xxx.ware_house.net.interceptor.HttpLogInterceptor
import com.xxx.ware_house.net.interceptor.NetCacheInterceptor
import com.xxx.ware_house.net.interceptor.OfflineCacheInterceptor
import com.xxx.ware_house.utils.LogUtils
import com.xxx.ware_house.utils.SpUtil
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @Author: ZhangRuixiang
 * Date: 2021/11/13
 * DES:
 */
class RetrofitManager private constructor() {
    private var okHttpClient: OkHttpClient? = null
    private var retrofit: Retrofit? = null
    val oneNetList: ArrayList<String>
    private var retrofitApiService: RetrofitApiService? = null

    init {
        oneNetList = ArrayList()
        initOkHttpClient()
        initRetrofit()
    }

    private fun initRetrofit() {
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
        retrofitApiService = retrofit?.create(retrofitApiServiceClass) as RetrofitApiService
    }

    private fun initOkHttpClient() {
        okHttpClient = OkHttpClient.Builder()
            .cache(
                Cache(
                    File(
                        Environment.getExternalStorageDirectory().toString() + "/okhttp_cache/"
                    ), (50 * 1024 * 1024).toLong()
                )
            )
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(HttpLogInterceptor(imageUrlList)) //设置在线和离线缓存
            .addInterceptor(OfflineCacheInterceptor.getInstance())
            .addNetworkInterceptor(NetCacheInterceptor.getInstance())
            .build()
    }

    fun getRetrofitApiService(): RetrofitApiService? {
        if (retrofitManager == null) {
            retrofitManager = instance
        }
        assert(retrofitManager != null)
        return retrofitManager!!.retrofitApiService
    }

    companion object {
        private var retrofitManager: RetrofitManager? = null
        private var baseUrl: String? = null
        private var imageUrlList: List<String>? = null
        private var retrofitApiServiceClass: Class<*>? = null

        /**
         * 第一次或者重置baseUrl时使用
         * 建议在application中初始化
         * @param baseUrl 不可空
         * @param list  不可空
         */
        fun getInstance(baseUrl: String?, list: List<String>?, retrofitApiService: Class<*>?) {
            if (retrofitManager == null) {
                synchronized(RetrofitManager::class.java) {
                    if (retrofitManager == null) {
                        Companion.baseUrl = baseUrl
                        imageUrlList = list
                        retrofitApiServiceClass = retrofitApiService
                        retrofitManager = RetrofitManager()
                    } else {
                        retrofitManager = null
                        getInstance(baseUrl, list, retrofitApiService)
                    }
                }
            } else {
                retrofitManager = null
                getInstance(baseUrl, list, retrofitApiService)
            }
        }

        val instance: RetrofitManager?
            get() {
                if (TextUtils.isEmpty(baseUrl)) {
                    LogUtils.e(RetrofitManager::class.java.simpleName, "baseUrl为空，无法初始化")
                    return null
                }
                if (retrofitManager == null) {
                    synchronized(RetrofitManager::class.java) {
                        if (retrofitManager == null) {
                            retrofitManager = RetrofitManager()
                        }
                    }
                }
                return retrofitManager
            }
    }
}