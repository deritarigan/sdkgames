package com.akggame.akg_sdk.ui.activity

import com.akggame.akg_sdk.dao.api.model.response.GameProductsResponse
import com.akggame.akg_sdk.rx.IView

interface PaymentIView : IView {

    fun doOnSuccess(data: GameProductsResponse)
    fun doOnError(message: String)
}