package com.sunrise.suzukanotes.entity.db

/**
 *@author: Sunrise
 *Date: 2021/4/6
 *Time: 10:27
 *Email: e1175132893@outlook.com
 */

data class RawCardRarity(
    var id: Int = -1,
    var card_id: Int = -1,
    var rarity: Int = -1,
    var race_dress_id: Int = -1,
    var live_dress_id: Int = -1,
    var outgame_dress_id: Int = -1,
    var mini_dress_id: Int = -1,
    var skill_set: Int = -1,
    var speed: Int = -1,
    var stamina: Int = -1,
    var pow: Int = -1,
    var guts: Int = -1,
    var wiz: Int = -1,
    var max_speed: Int = -1,
    var max_stamina: Int = -1,
    var max_pow: Int = -1,
    var max_guts: Int = -1,
    var max_wiz: Int = -1,
    var proper_distance_short: Int = -1,
    var proper_distance_mile: Int = -1,
    var proper_distance_middle: Int = -1,
    var proper_distance_long: Int = -1,
    var proper_running_style_nige: Int = -1,
    var proper_running_style_senko: Int = -1,
    var proper_running_style_sashi: Int = -1,
    var proper_running_style_oikomi: Int = -1,
    var proper_ground_turf: Int = -1,
    var proper_ground_dirt: Int = -1,
    var get_dress_id_1: Int = -1,
    var get_dress_id_2: Int = -1,
)
