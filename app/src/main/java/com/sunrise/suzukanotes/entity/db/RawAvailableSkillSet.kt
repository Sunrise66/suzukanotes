package com.sunrise.suzukanotes.entity.db

import com.sunrise.suzukanotes.common.DBHelper

/**
 *@author: Sunrise
 *Date: 2021/4/2
 *Time: 15:03
 *Email: e1175132893@outlook.com
 */
class RawAvailableSkillSet {
    var available_skill_set_id: Int = 0
    var skill_ids: String = ""

    fun getRawSkills(): List<RawSkill> {
        val ids = skill_ids.split(";")
        val resultList = ArrayList<RawSkill>()
        ids.forEach { id ->
            DBHelper.get().getRawSkill(id.toInt())?.let {
                resultList.add(it)
            }
        }
        return resultList
    }
}