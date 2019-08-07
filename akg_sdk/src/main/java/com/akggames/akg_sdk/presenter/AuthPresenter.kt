package com.akggames.akg_sdk.presenter

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import com.akggames.akg_sdk.dao.AuthDao
import com.akggames.akg_sdk.dao.api.model.request.FacebookAuthRequest
import com.akggames.akg_sdk.dao.api.model.request.PhoneAuthRequest
import com.akggames.akg_sdk.dao.api.model.response.BaseResponse
import com.akggames.akg_sdk.dao.api.model.response.FacebookAuthResponse
import com.akggames.akg_sdk.dao.api.model.response.PhoneAuthResponse
import com.akggames.akg_sdk.rx.IView
import com.akggames.akg_sdk.rx.RxObserver
import com.akggames.akg_sdk.ui.dialog.PhoneLoginDialogFragment
//import com.akggames.akg_sdk.ui.BaseActivity
import io.reactivex.disposables.Disposable

class AuthPresenter(val mIView: IView) {

    fun phoneLogin(model: PhoneAuthRequest, context: Context) {
        AuthDao().onPhoneAuth(model).subscribe(object : RxObserver<PhoneAuthResponse>(mIView, "") {
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
                t as PhoneAuthResponse

                Log.d("TESTING API", "onNext")
                if (t.meta?.code == 200) {
                    Toast.makeText(context, t.data?.token, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, t.data?.message, Toast.LENGTH_LONG).show()
                }
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                Log.d("TESTING API", "onError")
            }
        })
    }

    fun googleLogin(model: FacebookAuthRequest, context: Context) {
        AuthDao().onProviderAuth(model).subscribe(object : RxObserver<FacebookAuthResponse>(mIView, "") {
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
                t as FacebookAuthResponse

                Log.d("TESTING API", "onNext")
                if (t.meta?.code == 200) {
                    Toast.makeText(context, (t as FacebookAuthResponse).data?.token, Toast.LENGTH_LONG).show()

                } else {
                    Toast.makeText(context, (t as FacebookAuthResponse).data?.message, Toast.LENGTH_LONG).show()
                }
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                Log.d("TESTING API", "onError")
            }
        })
    }

    fun facebookLogin(model: FacebookAuthRequest, context: Context) {
        AuthDao().onProviderAuth(model).subscribe(object : RxObserver<FacebookAuthResponse>(mIView, "") {
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
                t as FacebookAuthResponse
                Log.d("TESTING API", "onNext")
                if (t.meta?.code == 200) {
                    Toast.makeText(context, (t as FacebookAuthResponse).data?.token, Toast.LENGTH_LONG).show()

                } else {
                    Toast.makeText(context, (t as FacebookAuthResponse).data?.message, Toast.LENGTH_LONG).show()
                }
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                Log.d("TESTING API", "onError")
            }
        })
    }


}