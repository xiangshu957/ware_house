package com.xxx.ware_house.data

import java.io.Serializable

/**
 * @Author: ZhangRuixiang
 * Date: 2023/7/26
 * DES:
 */
data class GoodsDetailResp(
    var lists: List<Detail>,
    var total: Int
)

data class Detail(
    var abnormalQuantity: Int? = null,
    var abnormalRemark: Any? = null,
    var acceptQuantity: Any? = null,
    var acceptState: Int? = null,
    var clientID: Int? = null,
    var ediField: String? = null,
    var goodsID: Int? = null,
    var goodsSkuID: Any? = null,
    var goodsStatus: Int? = null,
    var id: Int? = null,
    var inBoundOrderID: String? = null,
    var modifyCompany: String? = null,
    var modifyCompanyID: Int? = null,
    var modifyTime: String? = null,
    var modifyUserID: Int? = null,
    var modifyUserName: String? = null,
    var operateTime: String? = null,
    var orderQuantity: Int? = null,
    var placeCode: Any? = null,
    var placeID: Any? = null,
    var placeSource: Int? = null,
    var propertyID: Any? = null,
    var propertyInfo1: String = "",
    var propertyInfo10: String = "",
    var propertyInfo11: String = "",
    var propertyInfo12: String = "",
    var propertyInfo13: String = "",
    var propertyInfo14: String = "",
    var propertyInfo15: String = "",
    var propertyInfo16: String = "",
    var propertyInfo17: String = "",
    var propertyInfo18: String = "",
    var propertyInfo19: String = "",
    var propertyInfo2: String = "",
    var propertyInfo20: String = "",
    var propertyInfo21: String = "",
    var propertyInfo22: String = "",
    var propertyInfo23: String = "",
    var propertyInfo24: String = "",
    var propertyInfo3: String = "",
    var propertyInfo4: String = "",
    var propertyInfo5: String = "",
    var propertyInfo6: String = "",
    var propertyInfo7: String = "",
    var propertyInfo8: String = "",
    var propertyInfo9: String = "",
    var quantity1: Double? = null,
    var quantity2: Any? = null,
    var quantity3: Any? = null,
    var receivedQuantity: Any? = null,
    var remark: Any? = null,
    var shelvesQuantity: Any? = null,
    var state: Int? = null,
    var volumeDetail: Double? = null,
    var warehouseID: Any? = null,
    var weightDetail: Double? = null
):Serializable{
    var boxCode:String?=null
}