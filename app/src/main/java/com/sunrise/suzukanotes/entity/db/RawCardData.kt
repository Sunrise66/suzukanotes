package com.sunrise.suzukanotes.entity.db

import com.sunrise.suzukanotes.common.DBHelper
import com.sunrise.suzukanotes.entity.bean.Card
import com.sunrise.suzukanotes.entity.bean.Skill
import com.sunrise.suzukanotes.utils.beanMapper

/**
 *@author: Sunrise
 *Date: 2021/4/2
 *Time: 14:06
 *Email: e1175132893@outlook.com
 */
data class RawCardData(
    var id: Int = -1,
    var chara_id: Int = -1,
    var default_rarity: Int = -1,
    var available_skill_set_id: Int = -1,
    var talent_speed: Int = -1,
    var talent_stamina: Int = -1,
    var talent_pow: Int = -1,
    var talent_guts: Int = -1,
    var talent_wiz: Int = -1,
    var talent_groupId: Int = -1,
    var bg_id: Int = -1,
    var running_style: Int = -1,
    var details: String = ""
) {
    fun getCard(): Card {
        val bean = beanMapper(Card::class.java, this@RawCardData)
        val info = details.split(";")
        bean.name = info[0]
        bean.cv = info[1]
        bean.dormitory = info[2]
        bean.weight = info[3]
        bean.description = info[4].replace("\\n", "\n")
        bean.bitrh = info[5]
        bean.height = info[6]
        bean.grade = info[7]
        bean.self_introduction = info[8].replace("\\n", "\n")
        bean.good_at = info[9]
        bean.poor_at = info[10]
        bean.about_ear = info[11]
        bean.about_tail = info[12]
        bean.shoes = info[13]
        bean.about_family = info[14]
        DBHelper.get().getTitle(chara_id)?.let {
            bean.title = it
        }
        DBHelper.get().getRawAvailableSkillSet(available_skill_set_id)?.let {
            val result = ArrayList<Skill>()
            it.getRawSkills().forEach { raw ->
                result += raw.getSkill()
            }
            bean.available_skills = result
        }
        DBHelper.get().getRawCardRarities(chara_id)?.let {
            bean.rarityDatas = it
        }
        return bean
    }
}