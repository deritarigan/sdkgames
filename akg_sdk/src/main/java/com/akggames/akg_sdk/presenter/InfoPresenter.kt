package com.akggames.akg_sdk.presenter

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.akggames.akg_sdk.dao.MainDao
import com.akggames.akg_sdk.dao.api.model.response.BaseResponse
import com.akggames.akg_sdk.dao.api.model.response.CurrentUserResponse
import com.akggames.akg_sdk.rx.IView
import com.akggames.akg_sdk.rx.RxObserver
import com.akggames.akg_sdk.ui.dialog.menu.AccountIView
import com.akggames.akg_sdk.ui.dialog.register.OTPIView
import io.reactivex.disposables.Disposable

class InfoPresenter(val mIView: IView) {


    fun onGetCurrentUser(context: Context) {
        MainDao().onCheckCurrentUser(context).subscribe(object : RxObserver<CurrentUserResponse>(mIView, "") {
            override fun onNext(t: BaseResponse) {
                super.onNext(t)
                Log.d("TESTING API", "onNext")
                t as CurrentUserResponse
                if (t.meta?.code == 200) {
                    Toast.makeText(context, t.data?.id, Toast.LENGTH_LONG).show()
                    (mIView as AccountIView).doOnSuccess(t)
                } else {
                    Toast.makeText(context, t.data?.message, Toast.LENGTH_LONG).show()
                    (mIView as AccountIView).doOnError(t.data?.message)
                }
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }
}