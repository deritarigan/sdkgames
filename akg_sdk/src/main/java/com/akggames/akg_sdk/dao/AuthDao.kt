package com.akggames.akg_sdk.dao

import com.akggames.akg_sdk.dao.api.Api
import com.akggames.akg_sdk.dao.api.model.request.FacebookAuthRequest
import com.akggames.akg_sdk.dao.api.model.request.PhoneAuthRequest
import com.akggames.akg_sdk.dao.api.model.request.SendOtpRequest
import com.akggames.akg_sdk.dao.api.model.request.SignUpRequest
import com.akggames.akg_sdk.dao.api.model.response.BaseResponse
import com.akggames.akg_sdk.dao.api.model.response.FacebookAuthResponse
import com.akggames.akg_sdk.dao.api.model.response.PhoneAuthResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AuthDao() {

    fun onProviderAuth(model: FacebookAuthRequest): Observable<FacebookAuthResponse> {
        return Api.onProviderLogin(model)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun onPhoneAuth(model: PhoneAuthRequest): Observable<PhoneAuthResponse> {
        return Api.onPhoneLogin(model)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun onSendOtp(model: SendOtpRequest): Observable<BaseResponse> {
        return Api.onSendOtp(model)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun onCheckOtp(model: SendOtpRequest): Observable<BaseResponse> {
        return Api.onCheckOtp(model)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun onSignUp(model:SignUpRequest):Observable<BaseResponse>{
        return Api.onSignUp(model)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}