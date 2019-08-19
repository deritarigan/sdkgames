package com.akggames.akg_sdk.presenter

import android.content.Context
import com.akggames.akg_sdk.dao.MainDao
import com.akggames.akg_sdk.dao.api.model.request.ChangePasswordRequest
import com.akggames.akg_sdk.dao.api.model.response.BaseResponse
import com.akggames.akg_sdk.rx.IView
import com.akggames.akg_sdk.rx.RxObserver
import com.akggames.akg_sdk.ui.dialog.menu.ChangeIView
import io.reactivex.Observer

class UpdatePresenter(val mIView:IView) {
    fun onChangePassword(model:ChangePasswordRequest,context:Context){
        MainDao().onChangePassword(model,context).subscribe(object : RxObserver<BaseResponse>(mIView,""){
            override fun onNext(t: BaseResponse) {
                super.onNext(t)
                if (t.BaseMetaResponse?.code==200){
                    (mIView as ChangeIView).doOnSuccess(t)
                }else{
                    (mIView as ChangeIView).doOnError(t.BaseDataResponse?.message)
                }
            }
        })
    }
}