package com.sunrise.suzukanotes.entity.db

import com.sunrise.suzukanotes.entity.bean.Skill
import com.sunrise.suzukanotes.utils.beanMapper

/**
 *@author: Sunrise
 *Date: 2021/4/2
 *Time: 15:08
 *Email: e1175132893@outlook.com
 */
data class RawSkill(
    var id: Int = -1,
    var rarity: Int = -1,
    var group_id: Int = -1,
    var group_rate: Int = -1,
    var filter_switch: Int = -1,
    var grade_value: Int = -1,
    var skill_category: Int = -1,
    var tag_id: String = "",
    var unique_skill_id_1: Int = -1,
    var unique_skill_id_2: Int = -1,
    var exp_type: Int = -1,
    var potential_per_default: Int = -1,
    var activate_lot: Int = -1,
    var condition_1: String = "",
    var float_ability_time_1: Int = -1,
    var float_cooldown_time_1: Int = -1,
    var ability_type_1_1: Int = -1,
    var ability_value_usage_1_1: Int = -1,
    var ability_value_level_usage_1_1: Int = -1,
    var float_ability_value_1_1: Int = -1,
    var target_type_1_1: Int = -1,
    var target_value_1_1: Int = -1,
    var ability_type_1_2: Int = -1,
    var ability_value_usage_1_2: Int = -1,
    var ability_value_level_usage_1_2: Int = -1,
    var float_ability_value_1_2: Int = -1,
    var target_type_1_2: Int = -1,
    var target_value_1_2: Int = -1,
    var ability_type_1_3: Int = -1,
    var ability_value_usage_1_3: Int = -1,
    var ability_value_level_usage_1_3: Int = -1,
    var float_ability_value_1_3: Int = -1,
    var target_type_1_3: Int = -1,
    var target_value_1_3: Int = -1,
    var condition_2: String = "",
    var float_ability_time_2: Int = -1,
    var float_cooldown_time_2: Int = -1,
    var ability_type_2_1: Int = -1,
    var ability_value_usage_2_1: Int = -1,
    var ability_value_level_usage_2_1: Int = -1,
    var float_ability_value_2_1: Int = -1,
    var target_type_2_1: Int = -1,
    var target_value_2_1: Int = -1,
    var ability_type_2_2: Int = -1,
    var ability_value_usage_2_2: Int = -1,
    var ability_value_level_usage_2_2: Int = -1,
    var float_ability_value_2_2: Int = -1,
    var target_type_2_2: Int = -1,
    var target_value_2_2: Int = -1,
    var ability_type_2_3: Int = -1,
    var ability_value_usage_2_3: Int = -1,
    var ability_value_level_usage_2_3: Int = -1,
    var float_ability_value_2_3: Int = -1,
    var target_type_2_3: Int = -1,
    var target_value_2_3: Int = -1,
    var popularity_add_param_1: Int = -1,
    var popularity_add_value_1: Int = -1,
    var popularity_add_param_2: Int = -1,
    var popularity_add_value_2: Int = -1,
    var disp_order: Int = -1,
    var icon_id: Int = -1,
    var details: String = "",
    var need_rank:Int = -1,
) {

    fun getSkill(): Skill {
        val bean = beanMapper(Skill::class.java, this@RawSkill)
        val strs = details.split(";")
        bean.name = strs[0]
        bean.description = strs[1].replace("\\n", "\n")
        return bean
    }
}
