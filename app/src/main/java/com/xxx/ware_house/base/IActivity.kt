package com.xxx.ware_house.base

/**
 * @Author: ZhangRuixiang
 * Date: 2023/7/24
 * DES:
 */
interface IActivity {

    fun bindLayout():Int

    fun initView()

    fun initData()

    fun inject()

}
