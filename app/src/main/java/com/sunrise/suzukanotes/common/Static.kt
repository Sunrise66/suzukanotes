package com.sunrise.suzukanotes.common

import com.sunrise.easyframe.common.NetConfig

/**
 *@author: Sunrise
 *Date: 2021/3/30
 *Time: 10:34
 *Email: e1175132893@outlook.com
 */
object Static {

    private var BASE_URL = NetConfig.getInstance().PRIMARY_SERVER_ADDRESS

    @JvmField
    var DB_FILE_NAME = "master_jp.mdb"

    @JvmField
    var DB_FILE_NAME_COMPRESSED = "master_jp.mdb.br"

    val SKILL_ICON_URL = BASE_URL + "res/outgame/Texture2D/utx_ico_skill_%d.png"
    val CHARA_ICON_URL = BASE_URL + "res/chara/Texture2D/chr_icon_%d_%d01_02.png"
    val CHARA_STAND_ICON_URL = BASE_URL + "res/chara/Texture2D/chara_stand_%d_%d01.png"

    const val DB_FILE_NAME_JP = "master_jp.mdb"

    const val DB_FILE_NAME_COMPRESSED_JP = "master_jp.mdb.br"
    const val DB_FILE_NAME_CN = "master_cn.mdb"

    const val DB_FILE_NAME_COMPRESSED_CN = "master_cn.mdb.br"
    const val DB_FILE_NAME_KR = "master_kr.mdb"

    const val DB_FILE_NAME_COMPRESSED_KR = "master_kr.mdb.br"

    const val FIELD_SUIT_TURF = 0
    const val FIELD_SUIT_DIRT= 1

    const val FIELD_TURF = 1
    const val FIELD_DIRT= 0

    const val DISTANCE_SUIT_SHORT = 0
    const val DISTANCE_SUIT_MILE = 1
    const val DISTANCE_SUIT_MIDDLE = 2
    const val DISTANCE_SUIT_LONG = 3

    const val DISTANCE_SHORT = 0
    const val DISTANCE_MILE = 1
    const val DISTANCE_MIDDLE = 2
    const val DISTANCE_LONG = 3


    const val RUNNING_STYLE_NIGE = 1
    const val RUNNING_STYLE_SENKO = 2
    const val RUNNING_STYLE_SASHI = 3
    const val RUNNING_STYLE_OIKOMI = 4

    const val SORT_BY_FIELD = 0
    const val SORT_BY_DISTANCE = 1
    const val SORT_BY_RUNNING_STYLE = 3
    const val SORT_BY_NAME = 4
}