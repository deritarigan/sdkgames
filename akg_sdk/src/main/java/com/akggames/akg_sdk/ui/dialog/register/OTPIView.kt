package com.akggames.akg_sdk.ui.dialog.register

import com.akggames.akg_sdk.dao.api.model.response.BaseResponse
import com.akggames.akg_sdk.rx.IView

interface OTPIView : IView {

    fun doOnSuccessGenerate(data:BaseResponse)
    fun doOnSuccessCheck(data:BaseResponse)
}