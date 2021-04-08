package com.sunrise.suzukanotes.ui.chara

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sunrise.suzukanotes.entity.bean.Card
import com.sunrise.suzukanotes.share.CharaShareViewModel

class CharaViewModel(private val sharedViewModelChara: CharaShareViewModel) : ViewModel() {
    val charaLiveList = MutableLiveData<MutableList<Card>>()
    fun getCharaList(){
        charaLiveList.value = sharedViewModelChara.charaList.value
    }
}