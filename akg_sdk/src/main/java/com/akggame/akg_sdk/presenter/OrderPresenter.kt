package com.akggame.akg_sdk.presenter

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.akggame.akg_sdk.dao.MainDao
import com.akggame.akg_sdk.dao.api.model.request.PostOrderRequest
import com.akggame.akg_sdk.dao.api.model.response.BaseResponse
import com.akggame.akg_sdk.rx.IView
import com.akggame.akg_sdk.rx.RxObserver
import com.akggame.akg_sdk.ui.activity.PaymentIView
import com.android.billingclient.api.Purchase

class OrderPresenter(val mIView : IView) {


    fun onPostOrder(body:PostOrderRequest,purchase: Purchase,context: Context){
        MainDao().onPostOrder(body,context).subscribe(object :RxObserver<BaseResponse>(mIView,""){
            override fun onNext(t: BaseResponse) {
                super.onNext(t)
                if(t.BaseMetaResponse?.code==200){
                    (mIView as PaymentIView).doOnSuccessPost(t,purchase)
                }else{
                    Toast.makeText(context,"Error: "+t.BaseDataResponse?.message,Toast.LENGTH_LONG).show()
                }
            }

            override fun onComplete() {
                super.onComplete()
                (mIView as PaymentIView).doOnComplete(purchase)
            }
        })
    }
}