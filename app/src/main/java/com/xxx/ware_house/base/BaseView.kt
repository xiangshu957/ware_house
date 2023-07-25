package com.xxx.ware_house.base

import com.trello.rxlifecycle2.LifecycleTransformer

/**
 * @Author: ZhangRuixiang
 * Date: 2023/7/25
 * DES:
 */
open interface BaseView {
    //展示加载框
    fun showLoading(msg: String?)

    //隐藏加载框
    fun hideLoading()
    fun bindLifecycle(): LifecycleTransformer<*>?
}