package com.akggames.akg_sdk

import android.content.Context
import com.akggames.akg_sdk.ui.dialog.LoadingDialogComponent

interface IProgress {
    fun showProgressDialog(context: Context, message: String, isCanceledOnTouch: Boolean)

    fun showLoadingDialog(dialog: LoadingDialogComponent)
}