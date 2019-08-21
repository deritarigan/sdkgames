package com.akggames.akg_sdk.dao.api

import android.app.Application
import android.content.Context
import com.akggames.akg_sdk.IConfig
import com.akggames.akg_sdk.dao.api.model.request.*
import com.akggames.akg_sdk.dao.api.model.response.*
import com.akggames.akg_sdk.util.CacheUtil
import io.reactivex.Observable
import java.util.HashMap

class Api {
    companion object {

        private fun initHeader(): Map<String, String> {
            val map = HashMap<String, String>()
            map["Platform"] = "website"
            map["Cache-Control"] = "no-store"
            map["Content-Type"] = "application/json"

            return map
        }

        private fun initHeader(context: Context):Map<String,String>{
            val map = HashMap<String, String>()
            map["Authorization"]=CacheUtil.getPreferenceString(IConfig.SESSION_TOKEN,context).toString()
            map["Platform"] = "website"
            map["Cache-Control"] = "no-store"
            map["Content-Type"] = "application/json"

            return map
        }

        @Synchronized
        private fun initApiDomain(): IApi {
            return ApiManager.initApiService(
                IConfig.BaseUrl, true, IApi::class.java,
                IConfig.TIMEOUT_LONG_INSECOND, true
            ) as IApi
        }

        @Synchronized
        fun onProviderLogin(model: FacebookAuthRequest): Observable<FacebookAuthResponse> {
            return initApiDomain().callProviderLogin(initHeader(), model)
        }

        @Synchronized
        fun onPhoneLogin(model: PhoneAuthRequest): Observable<PhoneAuthResponse> {
            return initApiDomain().callPhoneLogin(initHeader(), model)
        }

        @Synchronized
        fun onGuestLogin(model:GuestLoginRequest,context: Context):Observable<PhoneAuthResponse>{
            return initApiDomain().callGuestLogin(initHeader(context),model)
        }
        @Synchronized
        fun onLogout(context: Context):Observable<BaseResponse>{
            return initApiDomain().callLogout(initHeader(context))
        }

        @Synchronized
        fun onSendOtp(model: SendOtpRequest): Observable<BaseResponse> {
            return initApiDomain().callSendOtp(initHeader(), model)
        }

        @Synchronized
        fun onCheckOtp(model: SendOtpRequest): Observable<BaseResponse> {
            return initApiDomain().callCheckOtp(initHeader(), model)
        }

        @Synchronized
        fun onSignUp(model: SignUpRequest): Observable<BaseResponse> {
            return initApiDomain().callSignUp(initHeader(), model)
        }

        @Synchronized
        fun onUpdatePassword(model:UpdatePasswordRequest):Observable<BaseResponse>{
            return initApiDomain().callUpdatePassword(initHeader(),model)
        }

        @Synchronized
        fun onGetCurrentUser(context: Context):Observable<CurrentUserResponse>{
            return initApiDomain().callGetCurrentUser(initHeader(context))
        }

        @Synchronized
        fun onChangePassword(body:ChangePasswordRequest,context: Context):Observable<BaseResponse>{
            return initApiDomain().callChangePassword(initHeader(context),body)
        }

        @Synchronized
        fun onGetProduct(gameProvider:String,context:Context):Observable<GameProductsResponse>{
            return initApiDomain().callGetProduct(initHeader(context),gameProvider)
        }
    }
}