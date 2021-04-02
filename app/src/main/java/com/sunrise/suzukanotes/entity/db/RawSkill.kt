package com.sunrise.suzukanotes.entity.db

import com.sunrise.suzukanotes.entity.bean.Skill

/**
 *@author: Sunrise
 *Date: 2021/4/2
 *Time: 15:08
 *Email: e1175132893@outlook.com
 */
class RawSkill(
    var id: Int,
    var rarity: Int,
    var group_id: Int,
    var group_rate: Int,
    var filter_switch: Int,
    var grade_value: Int,
    var skill_category: Int,
    var tag_id: String,
    var unique_skill_id_1: Int,
    var unique_skill_id_2: Int,
    var exp_type: Int,
    var potential_per_default: Int,
    var activate_lot: Int,
    var condition_1: String,
    var float_ability_time_1: Int,
    var float_cooldown_time_1: Int,
    var ability_type_1_1: Int,
    var ability_value_usage_1_1: Int,
    var ability_value_level_usage_1_1: Int,
    var float_ability_value_1_1: Int,
    var target_type_1_1: Int,
    var target_value_1_1: Int,
    var ability_type_1_2: Int,
    var ability_value_usage_1_2: Int,
    var ability_value_level_usage_1_2: Int,
    var float_ability_value_1_2: Int,
    var target_type_1_2: Int,
    var target_value_1_2: Int,
    var ability_type_1_3: Int,
    var ability_value_usage_1_3: Int,
    var ability_value_level_usage_1_3: Int,
    var float_ability_value_1_3: Int,
    var target_type_1_3: Int,
    var target_value_1_3: Int,
    var condition_2: String,
    var float_ability_time_2: Int,
    var float_cooldown_time_2: Int,
    var ability_type_2_1: Int,
    var ability_value_usage_2_1: Int,
    var ability_value_level_usage_2_1: Int,
    var float_ability_value_2_1: Int,
    var target_type_2_1: Int,
    var target_value_2_1: Int,
    var ability_type_2_2: Int,
    var ability_value_usage_2_2: Int,
    var ability_value_level_usage_2_2: Int,
    var float_ability_value_2_2: Int,
    var target_type_2_2: Int,
    var target_value_2_2: Int,
    var ability_type_2_3: Int,
    var ability_value_usage_2_3: Int,
    var ability_value_level_usage_2_3: Int,
    var float_ability_value_2_3: Int,
    var target_type_2_3: Int,
    var target_value_2_3: Int,
    var popularity_add_param_1: Int,
    var popularity_add_value_1: Int,
    var popularity_add_param_2: Int,
    var popularity_add_value_2: Int,
    var disp_order: Int,
    var icon_id: Int,
    var details: String,
) {
    fun getSkill(): Skill {
        val strs = details.split(";")
        return Skill(id, strs[0], strs[1])
    }
}
