package com.akggames.akg_sdk.ui.activity

import com.akggames.akg_sdk.dao.api.model.response.GameProductsResponse
import com.akggames.akg_sdk.rx.IView

interface PaymentIView : IView {

    fun doOnSuccess(data: GameProductsResponse)
    fun doOnError(message: String)
}