package com.akggame.akg_sdk.presenter

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akggame.akg_sdk.dao.MainDao
import com.akggame.akg_sdk.dao.api.model.response.BaseResponse
import com.akggame.akg_sdk.dao.api.model.response.CurrentUserResponse
import com.akggame.akg_sdk.rx.IView
import com.akggame.akg_sdk.rx.RxObserver
import com.akggame.akg_sdk.ui.dialog.menu.AccountIView
import com.akggame.akg_sdk.ui.dialog.register.OTPIView
import io.reactivex.disposables.Disposable

class InfoPresenter(val mIView: IView) {


    fun onGetCurrentUser(activity:AppCompatActivity,context: Context) {
        MainDao().onCheckCurrentUser(context).subscribe(object : RxObserver<CurrentUserResponse>(mIView, "") {
            override fun onNext(t: BaseResponse) {
                super.onNext(t)
                Log.d("TESTING API", "onNext")
                t as CurrentUserResponse
                if (t.meta?.code == 200) {
                    (mIView as AccountIView).doOnSuccess(activity,t)
                } else {
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