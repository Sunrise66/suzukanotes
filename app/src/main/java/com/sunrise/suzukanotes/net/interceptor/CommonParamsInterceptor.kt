package com.sunrise.suzukanotes.net.interceptor

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.sunrise.easyframe.net.interceptors.BasicParamsInterceptor
import com.sunrise.suzukanotes.Suzukanotes
import okhttp3.Interceptor

/**
 *@author: Sunrise
 *Date: 2021/3/23
 *Time: 16:23
 *Email: e1175132893@outlook.com
 */
object CommonParamsInterceptor {

    private val appInfo: ApplicationInfo by lazy {
        val context = Suzukanotes.getInstance()
        context.packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
    }

    @JvmStatic
    fun get(context: Context): Interceptor {
        return BasicParamsInterceptor.Builder().apply {
            injectHeaderParam {
                val params = HashMap<String, String>()
                params["source"] = "app"
                params["role"] = getRole()
                params["token"] = getToken()
                params
            }
        }.build()
    }

    private fun getRole(): String {
        return appInfo.metaData.getString("role").toString()
    }

    private fun getToken(): String {
        return appInfo.metaData.getString("token").toString()
    }
}
