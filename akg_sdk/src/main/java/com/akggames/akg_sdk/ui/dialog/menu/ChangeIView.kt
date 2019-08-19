package com.akggames.akg_sdk.ui.dialog.menu

import com.akggames.akg_sdk.dao.api.model.response.BaseResponse
import com.akggames.akg_sdk.dao.api.model.response.CurrentUserResponse
import com.akggames.akg_sdk.rx.IView

interface ChangeIView:IView {

    fun doOnSuccess(data: BaseResponse)
    fun doOnError(message:String?)
}