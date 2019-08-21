package com.akggames.akg_sdk.presenter

import android.content.Context
import android.util.Log
import com.akggames.akg_sdk.IConfig
import com.akggames.akg_sdk.IConfig.Companion.LOGIN_FACEBOOK
import com.akggames.akg_sdk.IConfig.Companion.LOGIN_GOOGLE
import com.akggames.akg_sdk.IConfig.Companion.LOGIN_GUEST
import com.akggames.akg_sdk.IConfig.Companion.LOGIN_PHONE
import com.akggames.akg_sdk.IConfig.Companion.LOGIN_TYPE
import com.akggames.akg_sdk.IConfig.Companion.SESSION_TOKEN
import com.akggames.akg_sdk.dao.MainDao
import com.akggames.akg_sdk.dao.api.model.request.FacebookAuthRequest
import com.akggames.akg_sdk.dao.api.model.request.GuestLoginRequest
import com.akggames.akg_sdk.dao.api.model.request.PhoneAuthRequest
import com.akggames.akg_sdk.dao.api.model.response.BaseResponse
import com.akggames.akg_sdk.dao.api.model.response.FacebookAuthResponse
import com.akggames.akg_sdk.dao.api.model.response.PhoneAuthResponse
import com.akggames.akg_sdk.rx.IView
import com.akggames.akg_sdk.rx.RxObserver
import com.akggames.akg_sdk.ui.dialog.login.LoginIView
import com.akggames.akg_sdk.util.CacheUtil
//import com.akggames.akg_sdk.ui.BaseActivity
import io.reactivex.disposables.Disposable

class LoginPresenter(val mIView: IView) {

    fun guestLogin(model:GuestLoginRequest,context: Context){
        MainDao().onGuestAuth(model,context).subscribe(object : RxObserver<BaseResponse>(mIView,
            ""){
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
                    (mIView as LoginIView).doOnSuccess("phone: "+t.data?.token!!)
                    CacheUtil.putPreferenceString(LOGIN_TYPE, LOGIN_GUEST, context)
                    CacheUtil.putPreferenceString(SESSION_TOKEN, t.data?.token!!, context)
                    CacheUtil.putPreferenceBoolean(IConfig.SESSION_LOGIN,true,context)

                } else {
                    (mIView as LoginIView).doOnError(t.data?.message!!)
                }
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                Log.d("TESTING API", "onError")
            }
        })
    }
    fun phoneLogin(model: PhoneAuthRequest, context: Context) {
        MainDao().onPhoneAuth(model).subscribe(object : RxObserver<PhoneAuthResponse>(mIView, "") {
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
                    (mIView as LoginIView).doOnSuccess("phone: "+t.data?.token!!)
                    CacheUtil.putPreferenceString(LOGIN_TYPE, LOGIN_PHONE, context)
                    CacheUtil.putPreferenceString(SESSION_TOKEN, t.data?.token!!, context)
                    CacheUtil.putPreferenceBoolean(IConfig.SESSION_LOGIN,true,context)

                } else {
                    (mIView as LoginIView).doOnError(t.data?.message!!)
                }
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                Log.d("TESTING API", "onError")
            }
        })
    }

    fun googleLogin(model: FacebookAuthRequest, context: Context) {
        MainDao().onProviderAuth(model).subscribe(object : RxObserver<FacebookAuthResponse>(mIView, "") {
            override fun onSubscribe(d: Disposable) {
                super.onSubscribe(d)
                Log.d("TESTING API", "onSubscribe")
            }

            override fun onNext(t: BaseResponse) {
                super.onNext(t)
                t as FacebookAuthResponse

                Log.d("TESTING API", "onNext")
                if (t.meta?.code == 200) {
                    (mIView as LoginIView).doOnSuccess("google: "+t.data?.token!!)
                    CacheUtil.putPreferenceString(LOGIN_TYPE, LOGIN_GOOGLE, context)
                    CacheUtil.putPreferenceString(SESSION_TOKEN, t.data?.token!!, context)
                    CacheUtil.putPreferenceBoolean(IConfig.SESSION_LOGIN,true,context)

                } else {
                    (mIView as LoginIView).doOnError(t.data?.message!!)
                }
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                Log.d("TESTING API", "onError")
            }
        })
    }

    fun facebookLogin(model: FacebookAuthRequest, context: Context) {
        MainDao().onProviderAuth(model).subscribe(object : RxObserver<FacebookAuthResponse>(mIView, "") {
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
                    (mIView as LoginIView).doOnSuccess("facebook: "+t.data?.token!!)
                    CacheUtil.putPreferenceString(LOGIN_TYPE, LOGIN_FACEBOOK, context)
                    CacheUtil.putPreferenceString(SESSION_TOKEN, t.data?.token!!, context)
                    CacheUtil.putPreferenceBoolean(IConfig.SESSION_LOGIN,true,context)

                } else {
                    (mIView as LoginIView).doOnError(t.data?.message!!)
                }
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                Log.d("TESTING API", "onError")
            }
        })
    }


}