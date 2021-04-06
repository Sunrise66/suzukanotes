package com.sunrise.suzukanotes.entity.bean

import com.sunrise.suzukanotes.common.DBHelper
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
    var available_skills: List<Skill>,
    var talent_speed: Int = -1,
    var talent_stamina: Int = -1,
    var talent_pow: Int = -1,
    var talent_guts: Int = -1,
    var talent_wiz: Int = -1,
    var talent_groupId: Int = -1,
    var bg_id: Int = -1,
    var running_style: Int = -1,
    var default_rarity: Int = -1,
    var rarityDatas: List<RawCardRarity>? = null,
) {

}