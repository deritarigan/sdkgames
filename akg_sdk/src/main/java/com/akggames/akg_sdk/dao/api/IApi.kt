package com.akggames.akg_sdk.dao.api

import com.akggames.akg_sdk.dao.api.model.request.*
import com.akggames.akg_sdk.dao.api.model.response.*
import retrofit2.http.*
import  io.reactivex.Observable

interface IApi {

    @POST("auth/provider/login")
    fun callProviderLogin(@HeaderMap map: Map<String, String>, @Body body: FacebookAuthRequest): Observable<FacebookAuthResponse>

    @POST("auth/login")
    fun callPhoneLogin(@HeaderMap map: Map<String, String>, @Body body: PhoneAuthRequest): Observable<PhoneAuthResponse>

    @POST("auth/guest/login")
    fun callGuestLogin(@HeaderMap map: Map<String, String>, @Body body: GuestLoginRequest): Observable<PhoneAuthResponse>

    @POST("auth/signup/send_otp")
    fun callSendOtp(@HeaderMap map: Map<String, String>, @Body body: SendOtpRequest): Observable<BaseResponse>

    @POST("auth/signup/check_otp")
    fun callCheckOtp(@HeaderMap map: Map<String, String>, @Body body: SendOtpRequest): Observable<BaseResponse>

    @POST("auth/signup")
    fun callSignUp(@HeaderMap map: Map<String, String>, @Body body: SignUpRequest): Observable<BaseResponse>

    @POST("auth/update_password")
    fun callUpdatePassword(@HeaderMap map: Map<String, String>, @Body body: UpdatePasswordRequest): Observable<BaseResponse>


    @DELETE("auth/logout")
    fun callLogout(@HeaderMap map: Map<String, String>): Observable<BaseResponse>

    @GET("auth/current_user")
    fun callGetCurrentUser(@HeaderMap map: Map<String, String>): Observable<CurrentUserResponse>

    @PUT("auth/change_password")
    fun callChangePassword(@HeaderMap map: Map<String, String>, @Body body: ChangePasswordRequest): Observable<BaseResponse>

    @GET("game_products/android/{game-provider}")
    fun callGetProduct(@HeaderMap map: Map<String, String>, @Path("game-provider") gameProvider: String?) :Observable<GameProductsResponse>

    @POST("account/binding")
    fun callBindAccountSocmed(@HeaderMap map:Map<String,String>,@Body body:BindSocMedRequest):
            Observable<BaseResponse>
}