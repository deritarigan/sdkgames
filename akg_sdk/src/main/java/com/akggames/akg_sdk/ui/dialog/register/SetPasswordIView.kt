package com.akggames.akg_sdk.ui.dialog.register

import com.akggames.akg_sdk.dao.api.model.response.BaseResponse
import com.akggames.akg_sdk.rx.IView

interface SetPasswordIView : IView {
    fun doOnSuccess(data:BaseResponse)
}