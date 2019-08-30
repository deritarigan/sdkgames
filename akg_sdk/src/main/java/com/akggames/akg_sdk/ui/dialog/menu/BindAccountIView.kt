package com.akggames.akg_sdk.ui.dialog.menu

import com.akggames.akg_sdk.dao.api.model.response.BaseResponse
import com.akggames.akg_sdk.rx.IView

interface BindAccountIView : IView {

    fun doOnSuccess(data:BaseResponse,socmedType:String)

    fun doOnError(message:String,socmedType: String)
}