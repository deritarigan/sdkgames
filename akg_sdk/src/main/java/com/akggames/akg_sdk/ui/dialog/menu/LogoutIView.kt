package com.akggames.akg_sdk.ui.dialog.menu

import com.akggames.akg_sdk.rx.IView

interface LogoutIView:IView {
    fun doSuccess()
    fun doError()
}