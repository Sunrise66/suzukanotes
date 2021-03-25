package com.sunrise.suzukanotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.sunrise.suzukanotes.common.UpdateManager
import com.sunrise.suzukanotes.model.IMainModel
import com.sunrise.suzukanotes.model.impl.MainModelImpl
import com.sunrise.suzukanotes.ui.main.MainFragment
import com.sunrise.suzukanotes.utils.FileUtils
import kotlinx.coroutines.launch

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

    private fun checkUpload() {
        lifecycleScope.launch{
            model.checkDBVersion(UpdateManager.getInstance().version)
        }
    }

    private fun checkDbFile(): Boolean {
        return FileUtils.checkFileAndSize(FileUtils.getDbFilePath(), 50)
    }
}