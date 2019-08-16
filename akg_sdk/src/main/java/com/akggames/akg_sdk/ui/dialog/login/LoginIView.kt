package com.akggames.akg_sdk.ui.dialog.login

import com.akggames.akg_sdk.rx.IView

interface LoginIView : IView {

    fun doOnSuccess(token:String)
    fun doOnError(message:String)
}