package com.xxx.ware_house.utils

import com.xxx.ware_house.data.BarCodeInfo

/**
 * @Author: ZhangRuixiang
 * Date: 2023/7/26
 * DES:
 */
object DecodeBarCode {

    fun deCodeBarCode(barCodeInfo: BarCodeInfo): BarCodeInfo {

        if (barCodeInfo.barCode.isNullOrEmpty()) {
            return barCodeInfo
        }
        if (barCodeInfo.barCode?.startsWith("01990") == true && (barCodeInfo.barCode?.length
                ?: 0) > 40
        ) {//奥地利

            barCodeInfo.weight = barCodeInfo.barCode?.substring(23, 25).plus(".")
                .plus(barCodeInfo.barCode?.substring(25, 27))
            barCodeInfo.produceDate = barCodeInfo.barCode?.substring(32, 40)

        } else if (barCodeInfo.barCode?.startsWith("01993") == true && (barCodeInfo.barCode?.length
                ?: 0) > 34
        ) {//澳大利亚

            barCodeInfo.weight = barCodeInfo.barCode?.substring(22, 24).plus(".")
                .plus(barCodeInfo.barCode?.substring(24, 26))
            barCodeInfo.produceDate = barCodeInfo.barCode?.substring(28, 34)

        } else if ((barCodeInfo.barCode?.startsWith("01900") == true ||
                    barCodeInfo.barCode?.startsWith("01908") == true ||
                    barCodeInfo.barCode?.startsWith("01907") == true)
            && (barCodeInfo.barCode?.length
                ?: 0) > 40
        ) {//美国

            barCodeInfo.weight = barCodeInfo.barCode?.substring(22, 24).plus(".")
                .plus(barCodeInfo.barCode?.substring(24, 26))

            barCodeInfo.produceDate = barCodeInfo.barCode?.substring(28, 34)

        } else if (barCodeInfo.barCode?.startsWith("61134") == true && (barCodeInfo.barCode?.length
                ?: 0) > 24
        ) {//乌拉圭

            barCodeInfo.weight = barCodeInfo.barCode?.substring(11, 13).plus(".")
                .plus(barCodeInfo.barCode?.substring(13, 17))
            barCodeInfo.produceDate = barCodeInfo.barCode?.substring(16, 24)

        } else if (barCodeInfo.barCode?.startsWith("00184") == true && (barCodeInfo.barCode?.length
                ?: 0) > 55
        ) { //西班牙
            barCodeInfo.weight = barCodeInfo.barCode?.substring(35, 37).plus(".")
                .plus(barCodeInfo.barCode?.substring(37, 39))
            barCodeInfo.produceDate = barCodeInfo.barCode?.substring(49, 55)
        } else if (barCodeInfo.barCode?.startsWith("02984") == true && (barCodeInfo.barCode?.length
                ?: 0) > 25
        ) { //西班牙
            barCodeInfo.weight = barCodeInfo.barCode?.substring(29, 31).plus(".")
                .plus(barCodeInfo.barCode?.substring(31, 33))
            barCodeInfo.produceDate = barCodeInfo.barCode?.substring(19, 25)
        }
        return barCodeInfo
    }

}