package com.sunrise.suzukanotes.base

import androidx.fragment.app.Fragment
import com.sunrise.suzukanotes.ui.card.CardFragment
import com.sunrise.suzukanotes.ui.chara.CharaFragment
import com.sunrise.suzukanotes.ui.race.RaceFragment
import java.lang.IndexOutOfBoundsException

/**
 *@author: JiangYu
 *Date: 2021/3/24
 *Time: 23:03
 *Email: jiangyu@haogroup.com
 */
const val CARD_INDEX = 0
const val CHARA_INDEX = 1
const val RACE_INDEX = 2

class TabAdapter {
    private val tabFragmentCreator: Map<Int, () -> Fragment> = mapOf(
        CARD_INDEX to { CardFragment.newInstance() },
        CHARA_INDEX to { CharaFragment.newInstance() },
        RACE_INDEX to { RaceFragment.newInstance() }
    )

    fun getItemCount() = tabFragmentCreator.size

    fun createFragment(index: Int): Fragment {
        return tabFragmentCreator[index]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}