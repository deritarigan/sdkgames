package com.akggames.akg_sdk.presenter

import android.content.Context
import android.widget.Toast
import com.akggames.akg_sdk.IConfig
import com.akggames.akg_sdk.dao.AuthDao
import com.akggames.akg_sdk.dao.api.model.response.BaseResponse
import com.akggames.akg_sdk.dao.api.model.response.FacebookAuthResponse
import com.akggames.akg_sdk.rx.IView
import com.akggames.akg_sdk.rx.RxObserver
import com.akggames.akg_sdk.ui.dialog.menu.LogoutIView
import com.akggames.akg_sdk.util.CacheUtil
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
                if (t.BaseMetaResponse?.code == 200 || t.BaseDataResponse?.message.equals("success")) {
                    (mIView as LogoutIView).doSuccess()
                    CacheUtil.putPreferenceString(IConfig.LOGIN_TYPE, "", context)
                    CacheUtil.putPreferenceString(IConfig.SESSION_TOKEN, "", context)
                    CacheUtil.putPreferenceBoolean(IConfig.SESSION_LOGIN,false,context)
                } else {
                    (mIView as LogoutIView).doError()
                }
            }

            override fun onError(e: Throwable) {
                super.onError(e)
            }
        })
    }
}