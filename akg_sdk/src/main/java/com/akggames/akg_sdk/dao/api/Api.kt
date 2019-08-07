package com.akggames.akg_sdk.dao.api

import com.akggames.akg_sdk.IConfig
import com.akggames.akg_sdk.dao.api.model.request.*
import com.akggames.akg_sdk.dao.api.model.response.BaseResponse
import com.akggames.akg_sdk.dao.api.model.response.FacebookAuthResponse
import com.akggames.akg_sdk.dao.api.model.response.PhoneAuthResponse
import io.reactivex.Observable
import java.util.HashMap

class Api {
    companion object {

        private fun initHeader(): Map<String, String> {
            val map = HashMap<String, String>()
            map["Token"] =
                "eyJhbGciOiJIUzUxMiJ9.eyJkYXRhIjoxLCJlbWFpbCI6InJhbmlhQGNsYXBwaW5nYXBlLmNvbSIsInBsYXRmb3JtIjoid2Vic2l0ZSIsImtleSI6IjdYdk0xYmJuc3I3V0VjbU9ubFNjTnpvcElnMm5MQStjbld5SDc0Z1oiLCJ0aW1lc3RhbXAiOiIyMDE4LTExLTA2IDExOjI0OjQwICswNzAwIn0.wSPAcZJV8VBUSG8DAp_laovF7dFDhLxVJGQZmmDs3PsEz6SBn7FE2qF7k1UoY5Qq30wqjTDZAho1a55Yy2Fctg"
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
    }
}