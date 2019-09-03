package com.akggame.akg_sdk.ui.dialog.login

import com.akggame.akg_sdk.rx.IView

interface LoginIView : IView {

    fun doOnSuccess(token:String)
    fun doOnError(message:String)
}