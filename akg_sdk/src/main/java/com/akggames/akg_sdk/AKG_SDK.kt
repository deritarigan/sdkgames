package com.akggames.akg_sdk

import android.content.Context
import androidx.core.content.ContextCompat
import com.akggames.akg_sdk.dao.api.model.FloatingItem
import com.akggames.akg_sdk.ui.component.FloatingButton
import com.akggames.android.sdk.R

object AKG_SDK {

    fun setFloatingButton(floatingButton:FloatingButton,context:Context){
        floatingButton.circleIcon.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.btn_akg_logo))
        floatingButton.addItem(FloatingItem(ContextCompat.getDrawable(context, R.mipmap.btn_bind_account)))
        floatingButton.addItem(FloatingItem(ContextCompat.getDrawable(context, R.mipmap.btn_verify_account)))
        floatingButton.addItem(FloatingItem(ContextCompat.getDrawable(context, R.mipmap.btn_fb)))
        floatingButton.addItem(FloatingItem(ContextCompat.getDrawable(context, R.mipmap.btn_eula)))
        floatingButton.addItem(FloatingItem(ContextCompat.getDrawable(context, R.mipmap.btn_contact_us)))
        floatingButton.addItem(FloatingItem(ContextCompat.getDrawable(context, R.mipmap.btn_sdk_version)))
        floatingButton.addItem(FloatingItem(ContextCompat.getDrawable(context, R.mipmap.btn_log_out)))
    }
}