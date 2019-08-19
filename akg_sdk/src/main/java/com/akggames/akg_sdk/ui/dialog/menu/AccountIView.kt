package com.akggames.akg_sdk.ui.dialog.menu

import com.akggames.akg_sdk.dao.api.model.response.CurrentUserResponse
import com.akggames.akg_sdk.rx.IView

interface AccountIView :IView {

    fun doOnSuccess(data:CurrentUserResponse)
    fun doOnError(message:String?)
}