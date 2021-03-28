package com.sunrise.suzukanotes.model

import com.sunrise.suzukanotes.entity.http.HttpResult

/**
 *@author: Sunrise
 *Date: 2021/3/25
 *Time: 14:00
 *Email: e1175132893@outlook.com
 */
interface IMainModel {
    suspend fun checkDBVersion(version: Int): HttpResult<Boolean>
    suspend fun getDBPath(): HttpResult<String>
}