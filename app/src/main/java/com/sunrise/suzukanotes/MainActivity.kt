package com.sunrise.suzukanotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sunrise.suzukanotes.common.Dialogs
import com.sunrise.suzukanotes.model.IMainModel
import com.sunrise.suzukanotes.model.impl.MainModelImpl
import com.sunrise.suzukanotes.ui.main.MainFragment
import com.sunrise.suzukanotes.utils.FileUtils

class MainActivity : AppCompatActivity() {

    private val model: IMainModel by lazy {
        MainModelImpl()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

    }

    private fun downloadDB(){

    }

    private fun checkUpdate() {
        if (!checkDbFile()){
            Dialogs.showNewDBVersionDialog(this,true,object :Dialogs.DialogCallback{
                override fun positiveCallback(v: View) {
                    downloadDB()
                }

                override fun negativeCallback(v: View) {

                }
            })
        }
    }

    private fun checkDbFile(): Boolean {
        return FileUtils.checkFileAndSize(FileUtils.getDbFilePath(), 50)
    }
}