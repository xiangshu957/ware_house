package com.xxx.ware_house.data

/**
 * @Author: ZhangRuixiang
 * Date: 2023/7/26
 * DES:
 */
data class BaseResp<T>(
    val code: Int,
    val data: T,
    val massge: String
)