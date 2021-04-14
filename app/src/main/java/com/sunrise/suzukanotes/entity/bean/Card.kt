package com.sunrise.suzukanotes.entity.bean

import com.sunrise.suzukanotes.common.Static
import com.sunrise.suzukanotes.entity.db.RawCardRarity


/**
 *@author: Sunrise
 *Date: 2021/4/6
 *Time: 14:47
 *Email: e1175132893@outlook.com
 */
data class Card(
    var chara_id: Int = -1,
    var name: String = "",
    var title: String = "",
    var cv: String = "",
    var dormitory: String = "",
    var grade: String = "",
    var self_introduction: String = "",
    var description: String = "",
    var height: String = "",
    var weight: String = "",
    var bitrh: String = "",
    var good_at: String = "",
    var poor_at: String = "",
    var about_ear: String = "",
    var about_tail: String = "",
    var shoes: String = "",
    var about_family: String = "",
    var available_skills: List<Skill>? = null,
    var talent_speed: Int = -1,
    var talent_stamina: Int = -1,
    var talent_pow: Int = -1,
    var talent_guts: Int = -1,
    var talent_wiz: Int = -1,
    var talent_groupId: Int = -1,
    var bg_id: Int = -1,
    var running_style: Int = -1,
    var ui_nameplate_color_1: String = "",
    var ui_nameplate_color_2: String = "",
    var default_rarity: Int = -1,
    var rarityDatas: List<RawCardRarity>? = null,
) {
    fun getIconUrl(): String {
        return Static.CHARA_ICON_URL.format(chara_id, chara_id)
    }

    fun getDistance(): Int {
        val data = rarityDatas?.get(0)
        val long = data?.proper_distance_long
        val middle = data?.proper_distance_middle
        val mile = data?.proper_distance_mile
        if (long == 7) {
            return Static.DISTANCE_LONG
        }
        if (middle == 7) {
            return Static.DISTANCE_MIDDLE
        }
        if (mile == 7) {
            return Static.DISTANCE_MILE
        }
        return Static.DISTANCE_SHORT
    }

    fun getField(): Int {
        val data = rarityDatas?.get(0)
        val turf = data?.proper_ground_turf
        val dirt = data?.proper_ground_dirt
        return if (turf!! > dirt!!) {
            Static.FIELD_TURF
        } else {
            Static.FIELD_DIRT
        }
    }
}