package com.akggames.akg_sdk.presenter

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.akggames.akg_sdk.dao.AuthDao
import com.akggames.akg_sdk.dao.api.model.request.SendOtpRequest
import com.akggames.akg_sdk.dao.api.model.request.SignUpRequest
import com.akggames.akg_sdk.dao.api.model.response.BaseResponse
import com.akggames.akg_sdk.dao.api.model.response.PhoneAuthResponse
import com.akggames.akg_sdk.rx.IView
import com.akggames.akg_sdk.rx.RxObserver
import com.akggames.akg_sdk.ui.dialog.register.OTPIView
import com.akggames.akg_sdk.ui.dialog.register.SetPasswordDialog
import io.reactivex.disposables.Disposable

class RegisterPresenter(val iView: IView) {

    fun sendOtp(model:SendOtpRequest,context:Context){
        AuthDao().onSendOtp(model).subscribe(object:RxObserver<BaseResponse>(iView,""){
            override fun onSubscribe(d: Disposable) {
                super.onSubscribe(d)
                Log.d("TESTING API", "onSubscribe")
            }

            override fun onComplete() {
                super.onComplete()
                Log.d("TESTING API", "onComplete")
            }

            override fun onNext(t: BaseResponse) {
                super.onNext(t)
                Log.d("TESTING API", "onNext")
                if (t.BaseMetaResponse?.code == 200) {
                    (iView as OTPIView).doOnSuccessGenerate(t)
                    Toast.makeText(context, t.BaseDataResponse?.message, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, t.BaseDataResponse?.message, Toast.LENGTH_LONG).show()
                }
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                Log.d("TESTING API", "onError")
            }
        })
    }

    fun onSignUp(model:SignUpRequest,context: Context){
        AuthDao().onSignUp(model).subscribe(object : RxObserver<BaseResponse>(iView,""){
            override fun onSubscribe(d: Disposable) {
                super.onSubscribe(d)
                Log.d("TESTING API", "onSubscribe")
            }

            override fun onComplete() {
                super.onComplete()
                Log.d("TESTING API", "onComplete")
            }

            override fun onNext(t: BaseResponse) {
                super.onNext(t)
                Log.d("TESTING API", "onNext")
                if (t.BaseMetaResponse?.code == 200) {
                    (iView as SetPasswordDialog).doOnSuccess(t)
                    Toast.makeText(context, t.BaseDataResponse?.message, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, t.BaseDataResponse?.message, Toast.LENGTH_LONG).show()
                }
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                Log.d("TESTING API", "onError")
            }
        })
    }

    fun checkOtp(model:SendOtpRequest,context:Context){
        AuthDao().onCheckOtp(model).subscribe(object:RxObserver<BaseResponse>(iView,""){
            override fun onSubscribe(d: Disposable) {
                super.onSubscribe(d)
                Log.d("TESTING API", "onSubscribe")
            }

            override fun onComplete() {
                super.onComplete()
                Log.d("TESTING API", "onComplete")
            }

            override fun onNext(t: BaseResponse) {
                super.onNext(t)
                Log.d("TESTING API", "onNext")
                if (t.BaseMetaResponse?.code == 200) {
                    Toast.makeText(context, t.BaseDataResponse?.message, Toast.LENGTH_LONG).show()
                    (iView as OTPIView).doOnSuccessCheck(t)
                } else {
                    Toast.makeText(context, t.BaseDataResponse?.message, Toast.LENGTH_LONG).show()
                }
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                Log.d("TESTING API", "onError")
            }
        })
    }


}