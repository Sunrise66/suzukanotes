package com.sunrise.suzukanotes.common

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.sunrise.easyframe.common.NetConfig
import com.sunrise.easyframe.delegates.AbsSharedPreference
import com.sunrise.suzukanotes.model.IMainModel
import com.sunrise.suzukanotes.model.impl.MainModelImpl
import com.sunrise.suzukanotes.utils.FileUtils
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

/**
 *@author: JiangYu
 *Date: 2021/3/28
 *Time: 21:09
 *Email: jiangyu@haogroup.com
 */
class UpdateHelper(context: Context) : AbsSharedPreference(context, "version_conf") {
    var dbVersion: Int by sharedPreference
    var dbFileLength: Int by sharedPreference
    private val model: IMainModel by lazy {
        MainModelImpl()
    }
    private var maxLength: Int = 0
    private var hasNewVersion = false
    private var versionInfo = 0
    private var progress = 0
    private var errmsg = ""
    var callback: UpdateCallback? = null

    companion object {
        private const val UPDATE_CHECK_COMPLETED = 1
        private const val UPDATE_DOWNLOADING = 2
        private const val UPDATE_DOWNLOAD_ERROR = 3
        private const val UPDATE_DOWNLOAD_COMPLETED = 4
        private const val UPDATE_COMPLETED = 5
        private const val UPDATE_DOWNLOAD_CANCELED = 6
        private const val APP_UPDATE_CHECK_COMPLETED = 11
        private const val DB_UPDATE_START = 7

        @Volatile
        private lateinit var instance: UpdateHelper

        @JvmStatic
        fun with(context: Context) {
            synchronized(UpdateHelper::class.java) {
                instance = UpdateHelper(context)
            }
        }

        @JvmStatic
        fun get(): UpdateHelper {
            return instance
        }

    }

    fun installDB() {
        updateHandler.sendMessage(updateHandler.obtainMessage(UPDATE_COMPLETED))
    }

    suspend fun checkDBVersion() {
        model.checkDBVersion().apply {
            if (code == 200) {
                hasNewVersion = dbVersion != data
                versionInfo = data
                updateHandler.sendMessage(updateHandler.obtainMessage(UPDATE_CHECK_COMPLETED))
            } else {
                errmsg = msg
                updateHandler.sendMessage(updateHandler.obtainMessage(UPDATE_DOWNLOAD_ERROR))
            }
        }
    }

    suspend fun downloadDB(isForce: Boolean) {
        if (isForce) {
            FileUtils.deleteDirectory(File(FileUtils.getDbDirectoryPath()))
        }
        var downloadUrl = ""
        model.getDBPath().apply {
            if (code == 200) {
                downloadUrl = content
            } else {
                errmsg = msg
                updateHandler.sendMessage(updateHandler.obtainMessage(UPDATE_DOWNLOAD_ERROR))
            }
        }
        thread(true) {
            try {
                Looper.prepare()
                updateHandler.sendMessage(updateHandler.obtainMessage(DB_UPDATE_START))
                val conn =
                    URL(NetConfig.getInstance().PRIMARY_SERVER_ADDRESS + downloadUrl).openConnection() as HttpURLConnection
                maxLength = conn.contentLength
                dbFileLength = maxLength
                val inputStream = conn.inputStream
                if (!File(FileUtils.getDbDirectoryPath()).exists()) {
                    if (!File(FileUtils.getDbDirectoryPath()).mkdirs()) throw Exception("Cannot create DB path.")
                }
                val dbFile = File(FileUtils.getDbFilePath())
                if (File(FileUtils.getDbDirectoryPath()).exists()) {
                    FileUtils.deleteFile(dbFile)
                }
                val fileOutputStream = FileOutputStream(dbFile)
                var totalDownload = 0
                val buf = ByteArray(1024 * 1024)
                var numRead: Int
                while (true) {
                    numRead = inputStream.read(buf)
                    totalDownload += numRead
                    progress = totalDownload
                    updateHandler.sendMessage(updateHandler.obtainMessage(UPDATE_DOWNLOADING))
                    if (numRead <= 0) {
                        updateHandler.sendEmptyMessage(UPDATE_DOWNLOAD_COMPLETED)
                        break
                    }
                    fileOutputStream.write(buf, 0, numRead)
                }
                inputStream.close()
                fileOutputStream.close()
                updateHandler.sendMessage(updateHandler.obtainMessage(UPDATE_DOWNLOAD_COMPLETED))
                Looper.loop()
            } catch (e: Exception) {
                e.printStackTrace()
                errmsg = e.toString()
                updateHandler.sendMessage(updateHandler.obtainMessage(UPDATE_DOWNLOAD_ERROR))
            }
        }
    }

    private val updateHandler = Handler(Looper.getMainLooper()) { msg: Message ->
        when (msg.what) {
            APP_UPDATE_CHECK_COMPLETED ->
                callback?.appCheckUpdateCompleted()
            DB_UPDATE_START ->
                callback?.dbDownloadStart()
            UPDATE_CHECK_COMPLETED ->
                callback?.dbCheckUpdateCompleted(hasNewVersion, versionInfo)
            UPDATE_DOWNLOADING ->
                callback?.dbDownloadProgressChanged(progress, maxLength)
            UPDATE_DOWNLOAD_ERROR ->
                callback?.dbUpdateError(errmsg)
            UPDATE_DOWNLOAD_COMPLETED ->
                callback?.dbDownloadCompleted(true, "Download Success")
            UPDATE_COMPLETED ->
                callback?.dbUpdateCompleted()
            UPDATE_DOWNLOAD_CANCELED ->
                TODO()
            else -> {
            }
        }
        true
    }

    interface UpdateCallback {
        fun appCheckUpdateCompleted()
        fun dbCheckUpdateCompleted(hasUpdate: Boolean, updateInfo: Int)
        fun dbDownloadStart()
        fun dbDownloadProgressChanged(progress: Int, maxLength: Int)
        fun dbUpdateError(msg: String)
        fun dbDownloadCompleted(success: Boolean, errorMsg: CharSequence? = null)
        fun dbUpdateCompleted()
    }
}