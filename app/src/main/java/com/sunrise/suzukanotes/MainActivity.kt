package com.sunrise.suzukanotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.sunrise.suzukanotes.common.Dialogs
import com.sunrise.suzukanotes.common.Static
import com.sunrise.suzukanotes.common.UpdateHelper
import com.sunrise.suzukanotes.model.IMainModel
import com.sunrise.suzukanotes.model.impl.MainModelImpl
import com.sunrise.suzukanotes.ui.main.MainFragment
import com.sunrise.suzukanotes.utils.FileUtils
import kotlinx.coroutines.launch
import per.goweii.anylayer.DialogLayer
import java.io.File

class MainActivity : AppCompatActivity(), UpdateHelper.UpdateCallback {

    private val model: IMainModel by lazy {
        MainModelImpl()
    }
    private var dbInfo = 0
    private var progressDialog: DialogLayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
        UpdateHelper.get().callback = this@MainActivity
        checkUpdate()
    }


    private fun checkUpdate() {
        if (!checkDbFile()) {
            Dialogs.showNewDBVersionDialog(this, true, object : Dialogs.NewDBVersionDialogCallback {
                override fun positiveCallback(v: View) {
                    this@MainActivity.lifecycleScope.launch {
                        UpdateHelper.get().downloadDB(true)
                    }
                }

                override fun negativeCallback(v: View) {

                }
            })
        } else {
            this@MainActivity.lifecycleScope.launch {
                UpdateHelper.get().checkDBVersion()
            }
        }
    }

    private fun checkDbFile(): Boolean {
        return if (!File(FileUtils.getDbFilePath()).exists()) {
            false
        } else {
            File(FileUtils.getDbFilePath()).length() >= UpdateHelper.get().dbFileLength
        }
    }

    /**
     * app版本检测完成回调
     */
    override fun appCheckUpdateCompleted() {

    }

    /**
     * 数据库版本检测完成回调
     */
    override fun dbCheckUpdateCompleted(hasUpdate: Boolean, updateInfo: Int) {
        if (hasUpdate) {
            Dialogs.showNewDBVersionDialog(
                this,
                false,
                object : Dialogs.NewDBVersionDialogCallback {
                    override fun positiveCallback(v: View) {
                        this@MainActivity.lifecycleScope.launch {
                            UpdateHelper.get().downloadDB(false)
                        }
                    }

                    override fun negativeCallback(v: View) {

                    }

                })
        }
    }

    /**
     * 数据库下载开始回调
     */
    override fun dbDownloadStart() {
        progressDialog = Dialogs.showDownloadDialog(this)
    }

    /**
     * 数据库下载中间回调
     */
    override fun dbDownloadProgressChanged(progress: Int, maxLength: Int) {
        if (progressDialog?.isShow!!) {
            progressDialog?.getView<TextView>(R.id.message)?.text =
                getString(
                    R.string.progress_dialog_message_downloading,
                    (progress / 1024),
                    maxLength / 1024
                )
        }
    }

    /**
     * 数据库下载错误回调
     */
    override fun dbUpdateError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * 数据库下载完成回调
     */
    override fun dbDownloadCompleted(success: Boolean, errorMsg: CharSequence?) {
        if (success) {
            if (progressDialog?.isShow!!) {
                progressDialog?.getView<TextView>(R.id.message)?.text =
                    getString(R.string.progress_dialog_message_install_db)
            }
            UpdateHelper.get().apply {
                doDecompress()
            }
        }
    }

    /**
     * 数据库升级完成回调
     */
    override fun dbUpdateCompleted(versionInfo: Int) {
        if (progressDialog?.isShow!!) {
            progressDialog?.getView<TextView>(R.id.message)?.text =
                getString(R.string.progress_dialog_message_install_db_complete)
            progressDialog?.getView<Button>(R.id.dialog_positive)?.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    progressDialog?.dismiss()
                }
            }
        }
        UpdateHelper.get().dbVersion = versionInfo
        setStatic()
    }

    private fun setStatic() {
        when (UpdateHelper.get().dbLocal) {
            "jp" -> {
                Static.DB_FILE_NAME = Static.DB_FILE_NAME_JP
                Static.DB_FILE_NAME_COMPRESSED = Static.DB_FILE_NAME_COMPRESSED_JP
            }
            "cn" -> {
                Static.DB_FILE_NAME = Static.DB_FILE_NAME_CN
                Static.DB_FILE_NAME_COMPRESSED = Static.DB_FILE_NAME_COMPRESSED_CN
            }
            "kr" -> {
                Static.DB_FILE_NAME = Static.DB_FILE_NAME_KR
                Static.DB_FILE_NAME_COMPRESSED = Static.DB_FILE_NAME_COMPRESSED_KR
            }
            else -> {
                UpdateHelper.get().dbLocal = "jp"
            }
        }
    }
}