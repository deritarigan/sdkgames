package com.akggames.akg_sdk

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.akggames.akg_sdk.dao.api.model.FloatingItem
import com.akggames.akg_sdk.ui.activity.PaymentActivity
import com.akggames.akg_sdk.ui.adapter.FloatingAdapterListener
import com.akggames.akg_sdk.ui.component.FloatingButton
import com.akggames.akg_sdk.ui.component.FloatingItemClickListener
import com.akggames.akg_sdk.ui.dialog.login.LoginDialogFragment
import com.akggames.akg_sdk.ui.dialog.menu.*
import com.akggames.akg_sdk.util.CacheUtil
import com.akggames.akg_sdk.util.DeviceUtil
import com.akggames.android.sdk.R

class AKG_SDK(val activity: AppCompatActivity) {

    private lateinit var customCallback: LoginSDKCallback
    private lateinit var menuCallback: MenuSDKCallback
    private lateinit var configuration: Configuration

    companion object {
        val SDK_PAYMENT_CODE = 199
    }


    fun setFloatingButton(floatingButton: FloatingButton, context: Context, menuSDKCallback: MenuSDKCallback) {
        menuCallback = menuSDKCallback
        val onItemClickListener: FloatingAdapterListener = object : FloatingAdapterListener {
            override fun onItemClick(position: Int, floatingItem: FloatingItem) {
                val contactUsDialog = InfoDialog()
                val checkVersionDialog = CheckVersionDialog()
                val bindAccountDialog = BindAccountDialog.newInstance(activity.supportFragmentManager)
                val logoutDialog = LogoutDialog.newInstance(menuCallback)
                val accountDialog = AccountDialog.newInstance(activity.supportFragmentManager)
                when (position) {
                    0 -> {
                        bindAccountDialog.show(activity.supportFragmentManager, "bind account")
                    }
                    1 -> {
                        accountDialog.show(activity.supportFragmentManager, "account")
                    }
                    2 -> Toast.makeText(context, "fb", Toast.LENGTH_LONG).show()
                    3 -> Toast.makeText(context, "eula", Toast.LENGTH_LONG).show()
                    4 -> {
                        contactUsDialog.show(activity.supportFragmentManager, "contact us")
                    }
                    5 -> {
                        checkVersionDialog.show(activity.supportFragmentManager, "check version")
                    }
                    6 -> {
                        logoutDialog.show(activity.supportFragmentManager, "logout")
                    }
                }
            }
        }

        floatingButton.floatingAdapterListener = onItemClickListener


        floatingButton.circleIcon.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.btn_akg_logo))
        floatingButton.addItem(FloatingItem(ContextCompat.getDrawable(context, R.mipmap.btn_bind_account)))
        floatingButton.addItem(FloatingItem(ContextCompat.getDrawable(context, R.mipmap.btn_verify_account)))
        floatingButton.addItem(FloatingItem(ContextCompat.getDrawable(context, R.mipmap.btn_fb)))
        floatingButton.addItem(FloatingItem(ContextCompat.getDrawable(context, R.mipmap.btn_eula)))
        floatingButton.addItem(FloatingItem(ContextCompat.getDrawable(context, R.mipmap.btn_contact_us)))
        floatingButton.addItem(FloatingItem(ContextCompat.getDrawable(context, R.mipmap.btn_sdk_version)))
        floatingButton.addItem(FloatingItem(ContextCompat.getDrawable(context, R.mipmap.btn_log_out)))
    }

    fun onLogin(loginSDKCallback: LoginSDKCallback) {
            if (!CacheUtil.getPreferenceBoolean(IConfig.SESSION_LOGIN, activity)) {
                this.customCallback = loginSDKCallback
                val loginDialogFragment =
                    LoginDialogFragment.newInstance(activity.supportFragmentManager, customCallback)
                val ftransaction = activity.supportFragmentManager.beginTransaction()
                ftransaction?.addToBackStack("login")
                loginDialogFragment.show(ftransaction, "login")
            } else {
                Toast.makeText(activity, "You already logged in", Toast.LENGTH_LONG).show()
            }
    }

    fun checkIsLogin(context: Context): Boolean {
        return CacheUtil.getPreferenceBoolean(IConfig.SESSION_LOGIN, context)
    }


    fun onSDKPayment() {
        val intent = Intent(activity, PaymentActivity::class.java)
        activity.startActivityForResult(intent, SDK_PAYMENT_CODE)
    }
}