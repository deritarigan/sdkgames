package com.akggames.akg_sdk.ui.dialog.forget

import com.akggames.akg_sdk.dao.api.model.response.BaseResponse
import com.akggames.akg_sdk.rx.IView

interface ForgetIView : IView {
    fun doOnSuccessGenerate(data: BaseResponse)
    fun doOnSuccessCheck(data:BaseResponse)
}