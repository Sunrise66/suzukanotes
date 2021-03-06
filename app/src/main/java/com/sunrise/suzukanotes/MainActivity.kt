package com.sunrise.suzukanotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.sunrise.suzukanotes.common.DBHelper
import com.sunrise.suzukanotes.common.Dialogs
import com.sunrise.suzukanotes.common.Static
import com.sunrise.suzukanotes.common.UpdateHelper
import com.sunrise.suzukanotes.share.CharaShareViewModel
import com.sunrise.suzukanotes.ui.main.MainFragment
import com.sunrise.suzukanotes.utils.FileUtils
import kotlinx.coroutines.launch
import per.goweii.anylayer.DialogLayer
import per.goweii.anylayer.Layer
import java.io.File
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), UpdateHelper.UpdateCallback,
    CharaShareViewModel.MasterCharaCallBack {

    private var progressDialog: DialogLayer? = null
    private val sharedChara: CharaShareViewModel by lazy {
        ViewModelProvider(this)[CharaShareViewModel::class.java].apply {
            callBack = this@MainActivity
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
        UpdateHelper.get().callback = this@MainActivity
        if (checkDbFile()) {
            loadData()
        } else {
            checkUpdate()
            sharedChara.charaList.value = mutableListOf()
        }
    }


    private fun showForceDBUpdate() {
        Dialogs.showNewDBVersionDialog(this, true, object : Dialogs.NewDBVersionDialogCallback {
            override fun positiveCallback(dialog: Layer, btn: Button) {
                this@MainActivity.lifecycleScope.launch {
                    UpdateHelper.get().downloadDB(true)
                }
            }

            override fun negativeCallback(dialog: Layer, btn: Button) {

            }
        })
    }

    private fun showNoForceDBUpdate() {
        Dialogs.showNewDBVersionDialog(
            this,
            false,
            object : Dialogs.NewDBVersionDialogCallback {
                override fun positiveCallback(dialog: Layer, btn: Button) {
                    this@MainActivity.lifecycleScope.launch {
                        UpdateHelper.get().downloadDB(false)
                    }
                }

                override fun negativeCallback(dialog: Layer, btn: Button) {

                }

            })
    }

    private fun checkUpdate() {
        if (!checkDbFile()) {
            showForceDBUpdate()
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
     * app????????????????????????
     */
    override fun appCheckUpdateCompleted() {

    }

    /**
     * ?????????????????????????????????
     */
    override fun dbCheckUpdateCompleted(hasUpdate: Boolean, updateInfo: Int) {
        if (hasUpdate) {
            showNoForceDBUpdate()
        }
    }


    /**
     * ???????????????????????????
     */
    override fun dbDownloadStart() {
        progressDialog = Dialogs.showDownloadDialog(this)
    }

    /**
     * ???????????????????????????
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
     * ???????????????????????????
     */
    override fun dbUpdateError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * ???????????????????????????
     */
    override fun dbDownloadCompleted(success: Boolean, errorMsg: CharSequence?) {
        if (success) {
            if (progressDialog?.isShow!!) {
                progressDialog?.getView<TextView>(R.id.message)?.text =
                    getString(R.string.progress_dialog_message_install_db)
            }
            thread(true) {
                UpdateHelper.get().apply {
                    DBHelper.get().close()
                    doDecompress()
                }
            }
        }
    }

    /**
     * ???????????????????????????
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
        clearData()
        loadData()
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

    private fun loadData() {
        sharedChara.loadData()
    }

    private fun clearData() {
        DBHelper.get().close()
        sharedChara.charaList.value = mutableListOf()
    }

    override fun charaLoadFinished(succeeded: Boolean) {
        if (!succeeded) {
            Log.e("dberr","loadFail!")
        }
        checkUpdate()
    }
}