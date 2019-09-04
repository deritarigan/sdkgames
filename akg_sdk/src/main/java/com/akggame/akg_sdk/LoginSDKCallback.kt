package com.akggame.akg_sdk


interface LoginSDKCallback {
    fun onResponseSuccess(token: String,loginType:String)
    fun onResponseFailed(message: String)
}