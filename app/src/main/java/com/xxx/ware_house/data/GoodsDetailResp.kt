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
    var propertyInfo1: String? = null,
    var propertyInfo10: String? = null,
    var propertyInfo11: String? = null,
    var propertyInfo12: String? = null,
    var propertyInfo13: Any? = null,
    var propertyInfo14: Any? = null,
    var propertyInfo15: Any? = null,
    var propertyInfo16: Any? = null,
    var propertyInfo17: Any? = null,
    var propertyInfo18: String? = null,
    var propertyInfo19: Any? = null,
    var propertyInfo2: String? = null,
    var propertyInfo20: Any? = null,
    var propertyInfo21: Any? = null,
    var propertyInfo22: Any? = null,
    var propertyInfo23: Any? = null,
    var propertyInfo24: Any? = null,
    var propertyInfo3: String? = null,
    var propertyInfo4: String? = null,
    var propertyInfo5: String? = null,
    var propertyInfo6: String? = null,
    var propertyInfo7: String? = null,
    var propertyInfo8: String? = null,
    var propertyInfo9: String? = null,
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