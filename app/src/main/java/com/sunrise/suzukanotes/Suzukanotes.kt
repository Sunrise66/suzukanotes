package com.sunrise.suzukanotes

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.sunrise.easyframe.EasyFrameStarter
import com.sunrise.suzukanotes.common.DBHelper
import com.sunrise.suzukanotes.common.I18N
import com.sunrise.suzukanotes.common.UpdateHelper
import com.sunrise.suzukanotes.utils.Utils
import java.util.*

/**
 *@author: Sunrise
 *Date: 2021/3/23
 *Time: 15:29
 *Email: e1175132893@outlook.com
 */
class Suzukanotes : Application() {

    companion object {
        @Volatile
        private lateinit var instance: Suzukanotes

        @JvmStatic
        val stacks = Stack<Activity>()

        fun init(application: Suzukanotes) {
            synchronized(Suzukanotes::class.java) {
                instance = application
            }
        }

        @JvmStatic
        fun getInstance(): Suzukanotes {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        init(this)
        I18N.application = this
        Utils.setApp(this)
        EasyFrameStarter.init(this)
        UpdateHelper.with(this)
        DBHelper.with(this)
        registerActivityLifecycleCallbacks(MyActivityLifeCycleCallBack())
    }

    private class MyActivityLifeCycleCallBack : ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            stacks += activity
        }

        override fun onActivityStarted(activity: Activity) {

        }

        override fun onActivityResumed(activity: Activity) {

        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {

        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {

        }

    }
}