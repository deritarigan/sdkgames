package com.akggame.akg_sdk.rx

import com.akggame.akg_sdk.dao.api.model.response.BaseResponse
import com.akggame.akg_sdk.ui.dialog.LoadingDialogComponent
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


open class RxObserver<P : BaseResponse>(val mIView: IView, val msgLoading: String) :
    Observer<BaseResponse> {
    private var iv: IView? = null
    private var messageLoading: String? = null
    private var compositeDisposable = CompositeDisposable()

    override fun onComplete() {
        compositeDisposable.clear()
    }

    override fun onSubscribe(d: Disposable) {
        compositeDisposable.add(d)
        var dialogLoading: LoadingDialogComponent? = null
//        val activity = iv!!.getCurrentActivity()
        if (messageLoading != null)
//            dialogLoading = LoadingDialogComponent.openLoadingDialog(activity, messageLoading)
            while (dialogLoading == null || dialogLoading!!.isShowing()) return
    }

    override fun onNext(t: BaseResponse) {
//        LoadingDialogComponent.closeLoadingDialog(iv!!.getCurrentActivity())
    }

    override fun onError(e: Throwable) {
//        LoadingDialogComponent.closeLoadingDialog(iv!!.getCurrentActivity())
//        SnackbarInternetConnection.show(
//            iv.getCurrentActivity().resources.getString(R.string.coconut_internet_fail_message),
//            iv
//        )
    }
}

