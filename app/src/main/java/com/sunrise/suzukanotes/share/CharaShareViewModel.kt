package com.sunrise.suzukanotes.share

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sunrise.suzukanotes.common.DBHelper
import com.sunrise.suzukanotes.entity.bean.Card
import kotlin.concurrent.thread

/**
 *@author: Sunrise
 *Date: 2021/3/29
 *Time: 15:40
 *Email: e1175132893@outlook.com
 */
class CharaShareViewModel : ViewModel() {
    val loadingFlag = MutableLiveData(false)
    val charaList = MutableLiveData<MutableList<Card>>()
    var selectedChara: Card? = null

    fun loadData() {
        var succeeded: Boolean
        if (charaList.value.isNullOrEmpty()) {
            loadingFlag.postValue(true)
            thread(true) {
                val innerCharaList = mutableListOf<Card>()
                try {
                    loadCharaData(innerCharaList)
                    succeeded = true
                } catch (ex: Exception) {
                    succeeded = false
                    innerCharaList.clear()
                    Log.e("loadCharaData",ex.message.toString())
                }
                charaList.postValue(innerCharaList)
                loadingFlag.postValue(false)
                callBack?.charaLoadFinished(succeeded)
            }
        }
    }

    private fun loadCharaData(innerCharaList: MutableList<Card>) {
        DBHelper.get().getRawCardDatas()?.forEach {
            innerCharaList.add(it.getCard())
        }
    }

    var callBack: MasterCharaCallBack? = null

    interface MasterCharaCallBack {
        fun charaLoadFinished(succeeded: Boolean)
    }
}