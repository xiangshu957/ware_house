package com.xxx.ware_house.data

/**
 * @Author: ZhangRuixiang
 * Date: 2023/7/26
 * DES:
 */
data class OddCodeReq(
    val clientID: String?=null,
    val current_page: Int?=1,
    val page_size: Int?=100
)