package com.akggames.akg_sdk.dao

import android.content.Context
import com.akggames.akg_sdk.dao.api.Api
import com.akggames.akg_sdk.dao.api.model.request.*
import com.akggames.akg_sdk.dao.api.model.response.BaseResponse
import com.akggames.akg_sdk.dao.api.model.response.CurrentUserResponse
import com.akggames.akg_sdk.dao.api.model.response.FacebookAuthResponse
import com.akggames.akg_sdk.dao.api.model.response.PhoneAuthResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainDao() {

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

    fun onLogout(context: Context):Observable<BaseResponse>{
        return Api.onLogout(context)
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

    fun onUpdatePassword(model:UpdatePasswordRequest):Observable<BaseResponse>{
        return Api.onUpdatePassword(model)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun onCheckCurrentUser(context: Context):Observable<CurrentUserResponse>{
        return Api.onGetCurrentUser(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun onChangePassword(body:ChangePasswordRequest,context: Context):Observable<BaseResponse>{
        return Api.onChangePassword(body,context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}