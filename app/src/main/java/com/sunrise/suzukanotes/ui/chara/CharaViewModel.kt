package com.sunrise.suzukanotes.ui.chara

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sunrise.suzukanotes.common.Static
import com.sunrise.suzukanotes.entity.bean.Card
import com.sunrise.suzukanotes.share.CharaShareViewModel

class CharaViewModel(private val sharedViewModelChara: CharaShareViewModel) : ViewModel() {
    val charaLiveList = MutableLiveData<MutableList<Card>>()
    var selectedPosition = MutableLiveData(0)
    var isAsc: Boolean = false
    var selectedField: Int = -1
    var selectedDistance: Int = -1
    var selectedRunningType: Int = -1
    var selectedSort: Int = -1
    var noDataFlag: Boolean = false

    fun filterDefault() {
        filter(-1, -1, -1, -1, false)
    }

    private fun filter(
        field: Int?,
        distance: Int?,
        runningType: Int?,
        sort: Int?,
        asc: Boolean?,
    ): MutableList<Card> {
        selectedField = field ?: selectedField
        selectedRunningType = runningType ?: selectedRunningType
        selectedDistance = distance ?: selectedDistance
        selectedSort = sort ?: selectedSort
        asc?.let { isAsc = it }
        val list2Show: MutableList<Card> = mutableListOf()
        sharedViewModelChara.charaList.value?.forEach {
            if (checkField(it) && checkDistance(it) && checkRunningStyle(it)) {
                list2Show.add(it)
            } else if (checkDistance(it)) {
                list2Show.add(it)
            } else if (checkRunningStyle(it)) {
                list2Show.add(it)
            } else if (checkDistance(it)) {
                list2Show.add(it)
            } else if (checkField(it) && checkDistance(it)) {
                list2Show.add(it)
            } else if (checkField(it) && checkRunningStyle(it)) {
                list2Show.add(it)
            } else if (checkDistance(it) && checkRunningStyle(it)) {
                list2Show.add(it)
            }
        }
        if (list2Show.isNullOrEmpty()) {
            noDataFlag = true
        } else {
            noDataFlag = false
            sortData(list2Show)
        }
        charaLiveList.postValue(list2Show)
        return list2Show
    }

    private fun sortData(charaList: MutableList<Card>) {
        if (-1 != selectedSort) {
            charaList.sortWith { a: Card, b: Card ->
                var valueA: Int
                var valueB: Int
                when (selectedSort) {
                    Static.SORT_BY_NAME -> {
                        valueA = 0
                        valueB = 0
                        a.name.toCharArray().forEach {
                            valueA += it.toInt()
                        }
                        b.name.toCharArray().forEach {
                            valueB += it.toInt()
                        }
                    }
                    Static.SORT_BY_RUNNING_STYLE -> {
                        valueA = a.running_style
                        valueB = b.running_style
                    }
                    Static.SORT_BY_DISTANCE -> {
                        valueA = a.getDistance()
                        valueB = b.getDistance()
                    }
                    Static.SORT_BY_FIELD -> {
                        valueA = a.getField()
                        valueB = b.getField()
                    }
                    else -> {
                        valueA = a.chara_id
                        valueB = b.chara_id
                    }
                }
                (if (isAsc) -1 else 1) * valueB.compareTo(valueA)
            }
        }
    }

    private fun checkField(chara: Card): Boolean {
        if (-1 != selectedField) {
            when (selectedField) {
                Static.FIELD_SUIT_DIRT ->
                    return 7 == chara.rarityDatas?.get(0)?.proper_ground_dirt
                Static.FIELD_SUIT_TURF ->
                    return 7 == chara.rarityDatas?.get(0)?.proper_ground_turf
            }
        }
        return true
    }

    private fun checkDistance(chara: Card): Boolean {
        if (-1 != selectedDistance) {
            when (selectedDistance) {
                Static.DISTANCE_SUIT_SHORT ->
                    return 7 == chara.rarityDatas?.get(0)?.proper_distance_short
                Static.DISTANCE_SUIT_MILE ->
                    return 7 == chara.rarityDatas?.get(0)?.proper_distance_mile
                Static.DISTANCE_SUIT_MIDDLE ->
                    return 7 == chara.rarityDatas?.get(0)?.proper_distance_middle
                Static.DISTANCE_SUIT_LONG ->
                    return 7 == chara.rarityDatas?.get(0)?.proper_distance_long
            }
        }
        return true
    }

    private fun checkRunningStyle(chara: Card): Boolean {
        if (-1 != selectedRunningType) {
            when (selectedRunningType) {
                Static.RUNNING_STYLE_NIGE ->
                    return 7 == chara.rarityDatas?.get(0)?.proper_running_style_nige
                Static.RUNNING_STYLE_SENKO ->
                    return 7 == chara.rarityDatas?.get(0)?.proper_running_style_senko
                Static.RUNNING_STYLE_SASHI ->
                    return 7 == chara.rarityDatas?.get(0)?.proper_running_style_sashi
                Static.RUNNING_STYLE_OIKOMI ->
                    return 7 == chara.rarityDatas?.get(0)?.proper_running_style_oikomi
            }
        }
        return true
    }
}