package com.akggames.akg_sdk.presenter

import android.content.Context
import com.akggames.akg_sdk.dao.AuthDao
import com.akggames.akg_sdk.dao.api.model.response.BaseResponse
import com.akggames.akg_sdk.rx.IView
import com.akggames.akg_sdk.rx.RxObserver
import io.reactivex.disposables.Disposable

class LogoutPresenter(val mIView: IView) {

    fun logout(context: Context){
        AuthDao().onLogout(context).subscribe(object: RxObserver<BaseResponse>(mIView,""){
            override fun onComplete() {
                super.onComplete()
            }

            override fun onSubscribe(d: Disposable) {
                super.onSubscribe(d)
            }

            override fun onNext(t: BaseResponse) {
                super.onNext(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
            }
        })
    }
}