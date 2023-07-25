package com.xxx.ware_house.api

import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * @Author: ZhangRuixiang
 * Date: 2021/11/13
 * DES:
 */
interface RetrofitApiService {
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