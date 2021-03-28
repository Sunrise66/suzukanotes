package com.sunrise.suzukanotes.common

import android.content.Context
import com.sunrise.easyframe.delegates.AbsSharedPreference
import com.sunrise.suzukanotes.model.IMainModel
import com.sunrise.suzukanotes.model.impl.MainModelImpl

/**
 *@author: JiangYu
 *Date: 2021/3/28
 *Time: 21:09
 *Email: jiangyu@haogroup.com
 */
class DownloadHelper(context: Context) : AbsSharedPreference(context, "version_conf") {
    private var dbVersion: Int by sharedPreference
    private val model: IMainModel by lazy {
        MainModelImpl()
    }

    companion object {
        @Volatile
        lateinit var instance: DownloadHelper

        @JvmStatic
        fun with(context: Context) {
            synchronized(DownloadHelper::class.java) {
                instance = DownloadHelper(context)
            }
        }

        @JvmStatic
        fun get(): DownloadHelper {
            return instance
        }

    }

    suspend fun checkDBVersion(): Boolean {
        return model.checkDBVersion(dbVersion).data
    }

    suspend fun downloadDB(){

    }
}