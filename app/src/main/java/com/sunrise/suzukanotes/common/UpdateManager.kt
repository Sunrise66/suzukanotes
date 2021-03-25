package com.sunrise.suzukanotes.common

import android.content.Context
import android.os.Handler
import android.os.Message
import androidx.annotation.StringRes
import com.sunrise.easyframe.delegates.AbsSharedPreference

/**
 *@author: Sunrise
 *Date: 2021/3/25
 *Time: 15:30
 *Email: e1175132893@outlook.com
 */
class UpdateManager(context: Context) : AbsSharedPreference(context, "db_info") {
    var version: Int by sharedPreference

    companion object {
        private const val UPDATE_CHECK_COMPLETED = 1
        private const val UPDATE_DOWNLOADING = 2
        private const val UPDATE_DOWNLOAD_ERROR = 3
        private const val UPDATE_DOWNLOAD_COMPLETED = 4
        private const val UPDATE_COMPLETED = 5
        private const val UPDATE_DOWNLOAD_CANCELED = 6
        private const val APP_UPDATE_CHECK_COMPLETED = 11

        @Volatile
        private lateinit var instance: UpdateManager

        @JvmStatic
        fun with(context: Context) {
            synchronized(UpdateManager::class.java) {
                instance = UpdateManager(context)
            }
        }

        @JvmStatic
        fun getInstance(): UpdateManager {
            return instance
        }
    }


//    val updateHandler = Handler(Handler.Callback { msg: Message ->
//        when (msg.what) {
//            APP_UPDATE_CHECK_COMPLETED ->
//                callBack.appCheckUpdateCompleted()
//            UPDATE_CHECK_COMPLETED ->
//                callBack.dbCheckUpdateCompleted(hasNewVersion, versionInfo)
//            UPDATE_DOWNLOADING ->
//                callBack.dbDownloadProgressChanged(progress, maxLength)
//            UPDATE_DOWNLOAD_ERROR ->
//                callBack.dbUpdateError()
//            UPDATE_DOWNLOAD_COMPLETED ->
//                callBack.dbDownloadCompleted(true, "")
//            UPDATE_COMPLETED ->
//                callBack.dbUpdateCompleted()
//            UPDATE_DOWNLOAD_CANCELED ->
//                TODO()
//            else -> {
//            }
//        }
//        true
//    })

    interface UpdateCallBack {
        fun appCheckUpdateCompleted()
        fun dbCheckUpdateCompleted(hasUpdate: Boolean, updateInfo: CharSequence?)
        fun dbDownloadProgressChanged(progress: Int, maxLength: Int)
        fun dbDownloadCanceled()
        fun dbUpdateError()
        fun dbDownloadCompleted(success: Boolean, errorMsg: CharSequence?)
        fun dbUpdateCompleted()
    }

    interface IActivityCallBack {
        fun showSnackBar(@StringRes messageRes: Int)
        fun dbDownloadFinished()
        fun dbUpdateFinished()
    }

    private var iActivityCallBack: IActivityCallBack? = null
    fun setIActivityCallBack(callBack: IActivityCallBack) {
        iActivityCallBack = callBack
    }
}