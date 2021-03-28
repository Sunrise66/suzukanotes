package com.sunrise.suzukanotes.net.api

import com.sunrise.suzukanotes.entity.http.HttpResult
import com.sunrise.suzukanotes.entity.http.ResReq
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 *@author: JiangYu
 *Date: 2021/3/23
 *Time: 22:26
 *Email: jiangyu@haogroup.com
 */
interface Api {

    interface ResourceApiController {
        @GET("/api/files/version")
        fun checkDBVersion(@Path("version") version: Int): Call<HttpResult<Boolean>>

        @POST("/api/files/master")
        fun getDBPath(): Call<HttpResult<String>>

        @POST("/api/image/png")
        fun getResourcePath(@Body request: ResReq): Call<HttpResult<String>>

        @POST("/api/image/base64")
        fun getResourceBase64(@Body request: ResReq): Call<HttpResult<String>>
    }

    interface CommonApiController {

    }
}