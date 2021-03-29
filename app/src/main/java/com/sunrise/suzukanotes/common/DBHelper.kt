package com.sunrise.suzukanotes.common

import android.app.Application
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 *@author: JiangYu
 *Date: 2021/3/28
 *Time: 10:55
 *Email: jiangyu@haogroup.com
 */
class DBHelper(application: Application) :
    SQLiteOpenHelper(application, "master.mdb", null, DB_VERSION) {
    companion object {
        const val DB_VERSION = 1

        @Volatile
        private lateinit var instance: DBHelper

        fun with(application: Application): DBHelper {
            synchronized(DBHelper::class.java) {
                instance = DBHelper(application)
            }
            return instance
        }

        @JvmStatic
        fun get(): DBHelper = instance
    }

    override fun onCreate(db: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}