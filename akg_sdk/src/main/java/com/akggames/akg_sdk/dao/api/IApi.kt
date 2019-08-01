package com.akggames.akg_sdk.dao.api

import com.akggames.akg_sdk.dao.api.model.request.FacebookAuthRequest
import com.akggames.akg_sdk.dao.api.model.response.FacebookAuthResponse
import retrofit2.http.*
import java.util.*
import  io.reactivex.Observable

interface IApi {

    @POST("auth/provider/login")
    fun callAuthLogin(@HeaderMap map: Map<String,String>,@Body body:FacebookAuthRequest):Observable<FacebookAuthResponse>


}