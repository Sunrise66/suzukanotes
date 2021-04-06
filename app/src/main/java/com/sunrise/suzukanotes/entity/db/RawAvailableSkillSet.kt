package com.sunrise.suzukanotes.entity.db

import com.sunrise.suzukanotes.common.DBHelper

/**
 *@author: Sunrise
 *Date: 2021/4/2
 *Time: 15:03
 *Email: e1175132893@outlook.com
 */
data class RawAvailableSkillSet(
    var available_skill_set_id: Int = -1,
    var need_rank:Int = -1,
    var skill_ids: String = ""
) {
    fun getRawSkills(): List<RawSkill> {
        val ids = skill_ids.split(";")
        val resultList = ArrayList<RawSkill>()
        ids.forEach { id ->
            DBHelper.get().getRawSkill(id.toInt())?.let {
                it.need_rank = this@RawAvailableSkillSet.need_rank
                resultList.add(it)
            }
        }
        return resultList
    }
}