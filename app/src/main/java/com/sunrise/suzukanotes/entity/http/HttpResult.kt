package com.sunrise.suzukanotes.entity.http

/**
 *@author: JiangYu
 *Date: 2021/3/23
 *Time: 22:30
 *Email: jiangyu@haogroup.com
 */
data class HttpResult<T>(
    val code: Int = 200,
    val data: T,
    val msg: String = "OK"
)
