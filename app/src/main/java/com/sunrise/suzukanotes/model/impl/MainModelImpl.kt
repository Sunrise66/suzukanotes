package com.sunrise.suzukanotes.model.impl

import com.sunrise.suzukanotes.entity.http.HttpResult
import com.sunrise.suzukanotes.entity.http.StringResult
import com.sunrise.suzukanotes.model.IMainModel
import com.sunrise.suzukanotes.net.SuzukanotesService
import com.sunrise.suzukanotes.net.api.Api
import retrofit2.await

/**
 *@author: Sunrise
 *Date: 2021/3/25
 *Time: 14:02
 *Email: e1175132893@outlook.com
 */
class MainModelImpl : IMainModel {
    private val api = SuzukanotesService.createApi(Api.ResourceApiController::class.java)

    override suspend fun checkDBVersion(local: String): HttpResult<Int> {
        return api.checkDBVersion(local).await()
    }

    override suspend fun getDBPath(local: String): StringResult {
        return api.getDBPath(local).await()
    }

}