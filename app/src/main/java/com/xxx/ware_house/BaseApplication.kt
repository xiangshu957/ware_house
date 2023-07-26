package com.xxx.ware_house

import android.app.Application
import com.xxx.ware_house.api.RetrofitApiService
import com.xxx.ware_house.net.RetrofitManager
import com.xxx.ware_house.utils.SpUtil

/**
 * @Author: ZhangRuixiang
 * Date: 2023/7/24
 * DES:
 */
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        RetrofitManager.getInstance("http://api.shiquan56.com:8080", listOf(),RetrofitApiService::class.java)

        SpUtil.init(this)
    }

}