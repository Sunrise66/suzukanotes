package com.sunrise.suzukanotes.net

import android.app.Application
import android.content.Context
import com.sunrise.easyframe.common.NetConfig
import com.sunrise.easyframe.net.CommonService
import com.sunrise.suzukanotes.net.interceptor.CommonParamsInterceptor
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

/**
 *@author: JiangYu
 *Date: 2021/3/23
 *Time: 21:46
 *Email: jiangyu@haogroup.com
 */
object SuzukanotesService {

    @JvmStatic
    fun <T : Any> createApi(apiClass: Class<T>): T {
        return getRetrofit().create(apiClass)
    }

    private fun getRetrofit(): Retrofit {
        return CommonService.getCustomRetrofit(
            NetConfig.getInstance().PRIMARY_SERVER_ADDRESS,
            CommonParamsInterceptor.get(),
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        )
    }
}