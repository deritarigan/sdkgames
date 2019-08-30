package com.akggames.akg_sdk.presenter

import android.content.Context
import com.akggames.akg_sdk.dao.MainDao
import com.akggames.akg_sdk.dao.api.model.response.BaseResponse
import com.akggames.akg_sdk.dao.api.model.response.GameProductsResponse
import com.akggames.akg_sdk.rx.IView
import com.akggames.akg_sdk.rx.RxObserver
import com.akggames.akg_sdk.ui.activity.PaymentIView
import io.reactivex.disposables.Disposable

class ProductPresenter(val mIView: IView) {

    fun getProducts(gameProvider: String?, context: Context) {
        MainDao().onGetProduct(gameProvider, context).subscribe(object : RxObserver<GameProductsResponse>(mIView, "") {
            override fun onComplete() {
                super.onComplete()
            }

            override fun onSubscribe(d: Disposable) {
                super.onSubscribe(d)
            }

            override fun onNext(t: BaseResponse) {
                super.onNext(t)
                (mIView as PaymentIView).doOnSuccess(t as GameProductsResponse)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
            }
        })
    }
}