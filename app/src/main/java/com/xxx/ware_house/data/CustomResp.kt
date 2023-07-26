package com.xxx.ware_house.data

import java.io.Serializable

/**
 * @Author: ZhangRuixiang
 * Date: 2023/7/26
 * DES:
 */
data class CustomResp(
    val lists: MutableList<Lists>? = null,
    val total: Int? = null
)

data class Lists(
    val address: String? = null,
    val bankAccount: Any? = null,
    val banks: Any? = null,
    val chargingLevel: Int? = null,
    val contactPeople: String? = null,
    val contactTel: String? = null,
    val customerCode: String? = null,
    val customerName: String? = null,
    val id: Int? = null,
    val invoiceTitle: Any? = null,
    val loginCode: Any? = null,
    val loginPassword: Any? = null,
    val networkQueryYN: Int? = null,
    val operatOrg: String? = null,
    val operateCode: String? = null,
    val operateLevel: Int? = null,
    val operateTime: String? = null,
    val paymentDay: String? = null,
    val postCode: String? = null,
    val reconciliationDate: String? = null,
    val state: Int? = null,
    val taxNumber: Any? = null,
    val updateCode: String? = null,
    val updateTime: String? = null
):Serializable{
    override fun toString(): String {
        return "$customerName"
    }
}