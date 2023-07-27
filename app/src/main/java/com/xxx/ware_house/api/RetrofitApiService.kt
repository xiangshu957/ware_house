package com.xxx.ware_house.api

import com.xxx.ware_house.data.BaseResp
import com.xxx.ware_house.data.CustomResp
import com.xxx.ware_house.data.CustomerReq
import com.xxx.ware_house.data.GoodsDetailResp
import com.xxx.ware_house.data.LoginReq
import com.xxx.ware_house.data.OddCodeReq
import com.xxx.ware_house.data.OddCodeResp
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.HeaderMap
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * @Author: ZhangRuixiang
 * Date: 2021/11/13
 * DES:
 */
interface RetrofitApiService {

    /**
     * 登录
     */
    @POST("/api/Login/UserLogin_V1")
    fun login(@Body loginReq: LoginReq): Observable<BaseResp<String>>

    /**
     * 获取客户
     */
    @POST("/api/CustomerInfo/Query_MDM_CustomerInfo_V1")
    fun getCustomer(
        @HeaderMap headerMap: Map<String, String>,
        @Body customerReq: CustomerReq
    ): Observable<BaseResp<CustomResp>>

    /**
     * 获取入库单号
     */
    @POST("http://api.shiquan56.com:8093/api/InWarehouse/QueryWMS_In_Bound_V1")
    fun getOddCodeList(
        @HeaderMap headerMap: Map<String, String>,
        @Body oddCodeReq: OddCodeReq
    ): Observable<BaseResp<OddCodeResp>>

    /**
     * 获取明细
     */
    @GET("http://api.shiquan56.com:8093/api/InWarehouse/QueryWMS_In_BoundList_V1")
    fun getGoodsDetail(
        @HeaderMap headerMap: Map<String, String>,
        @Query("InBoundOrderID") InBoundOrderID: String
    ): Observable<BaseResp<GoodsDetailResp>>

    /**
     * 保存
     */
    @POST("http://api.shiquan56.com:8093/api/InWarehouse/Insert_ReceiveGoodsDetail_PDA_V1")
    fun uploadReceive(@HeaderMap headerMap: Map<String, String>, @Body details: RequestBody):Observable<BaseResp<Any>>

    //Retrofit上传文件
    @POST
    @Multipart
    fun uploadFile(
        @Url url: String?,
        @Part("sequence") sequence: RequestBody?,
        @Part file: MultipartBody.Part?
    ): Observable<ResponseBody?>?

    //Retrofit下载文件
    @GET
    @Streaming
    fun  //10以上@Streaming。不会造成oom
            downloadFile(@Url url: String?): Observable<ResponseBody?>?

    @GET
    @Streaming
    fun downloadFile(@Url url: String?, @Header("RANGE") range: String?): Observable<ResponseBody?>?
}