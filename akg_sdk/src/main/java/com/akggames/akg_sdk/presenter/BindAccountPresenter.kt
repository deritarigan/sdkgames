package com.akggames.akg_sdk.presenter

import android.content.Context
import com.akggames.akg_sdk.dao.MainDao
import com.akggames.akg_sdk.dao.api.model.request.BindSocMedRequest
import com.akggames.akg_sdk.dao.api.model.response.BaseResponse
import com.akggames.akg_sdk.rx.IView
import com.akggames.akg_sdk.rx.RxObserver
import com.akggames.akg_sdk.ui.dialog.menu.BindAccountIView

class BindAccountPresenter(val mIView:IView) {


    fun onBindAccount(body:BindSocMedRequest,socmedType:String,context:Context){
        MainDao().onBindProduct(body,context).subscribe(object:RxObserver<BaseResponse>(mIView,""){
            override fun onNext(t: BaseResponse) {
                super.onNext(t)
                if (t.BaseMetaResponse?.code==200){
                    (mIView as BindAccountIView).doOnSuccess(t,socmedType)
                }else{
                    (mIView as BindAccountIView).doOnError(t.BaseDataResponse?.message.toString(),socmedType)
                }
            }
        })
    }
}