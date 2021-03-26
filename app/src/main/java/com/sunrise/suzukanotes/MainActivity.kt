package com.sunrise.suzukanotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.sunrise.suzukanotes.model.IMainModel
import com.sunrise.suzukanotes.model.impl.MainModelImpl
import com.sunrise.suzukanotes.ui.main.MainFragment
import com.sunrise.suzukanotes.utils.FileUtils
import per.goweii.anylayer.AnyLayer

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
        AnyLayer.dialog(this).apply {
            contentView(R.layout.normal_dialog)
            gravity(Gravity.CENTER_VERTICAL)
            backgroundColorRes(R.color.black)
            bindData {
                getView<TextView>(R.id.dialog_title).text="更新"
                getView<TextView>(R.id.message).text="有更新"
                getView<Button>(R.id.dialog_positive).visibility = View.GONE
            }
        }.show()
    }


    private fun checkUpload() {
//        lifecycleScope.launch{
//            model.checkDBVersion(UpdateManager.getInstance().version)
//        }
    }

    private fun checkDbFile(): Boolean {
        return FileUtils.checkFileAndSize(FileUtils.getDbFilePath(), 50)
    }
}