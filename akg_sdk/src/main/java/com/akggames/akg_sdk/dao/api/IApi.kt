package com.akggames.akg_sdk.dao.api

import com.akggames.akg_sdk.dao.api.model.request.*
import com.akggames.akg_sdk.dao.api.model.response.BaseResponse
import com.akggames.akg_sdk.dao.api.model.response.FacebookAuthResponse
import com.akggames.akg_sdk.dao.api.model.response.PhoneAuthResponse
import retrofit2.http.*
import java.util.*
import  io.reactivex.Observable

interface IApi {

    @POST("auth/provider/login")
    fun callProviderLogin(@HeaderMap map: Map<String, String>, @Body body: FacebookAuthRequest): Observable<FacebookAuthResponse>

    @POST("auth/login")
    fun callPhoneLogin(@HeaderMap map: Map<String, String>, @Body body: PhoneAuthRequest): Observable<PhoneAuthResponse>

    @POST("auth/signup/send_otp")
    fun callSendOtp(@HeaderMap map: Map<String, String>, @Body body: SendOtpRequest): Observable<BaseResponse>

    @POST("auth/signup/check_otp")
    fun callCheckOtp(@HeaderMap map: Map<String, String>, @Body body: SendOtpRequest): Observable<BaseResponse>

    @POST("auth/signup")
    fun callSignUp(@HeaderMap map: Map<String, String>, @Body body: SignUpRequest): Observable<BaseResponse>

    @POST("auth/update_password")
    fun callUpdatePassword(@HeaderMap map: Map<String, String>, @Body body: UpdatePasswordRequest): Observable<BaseResponse>
}