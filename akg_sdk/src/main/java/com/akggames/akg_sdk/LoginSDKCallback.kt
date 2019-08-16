package com.akggames.akg_sdk


interface LoginSDKCallback {
    fun onResponseSuccess(token: String)
    fun onResponseFailed(message: String)
}