package com.xxx.ware_house.data

/**
 * @Author: ZhangRuixiang
 * Date: 2023/7/26
 * DES:
 */
data class CustomerReq(
    val current_page: Int? = null,
    val customerCode: String? = null,
    val customerName: String? = null,
    val page_size: Int? = null,
    val sysType: Int? = null
)