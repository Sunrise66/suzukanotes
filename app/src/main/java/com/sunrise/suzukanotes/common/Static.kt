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

    const val DB_FILE_NAME_JP = "master_jp.mdb"
    const val DB_FILE_NAME_COMPRESSED_JP = "master_jp.mdb.br"

    const val DB_FILE_NAME_CN = "master_cn.mdb"
    const val DB_FILE_NAME_COMPRESSED_CN = "master_cn.mdb.br"

    const val DB_FILE_NAME_KR = "master_kr.mdb"
    const val DB_FILE_NAME_COMPRESSED_KR = "master_kr.mdb.br"

    val SKILL_ICON_URL = BASE_URL + "res/outgame/Texture2D/utx_ico_skill_%d.png"
    val CHARA_ICON_URL = BASE_URL + "res/chara/Texture2D/chr_icon_%d_%d01_02.png"
    val CHARA_STAND_ICON_URL = BASE_URL + "res/chara/Texture2D/chara_stand_%d_%d01.png"


}