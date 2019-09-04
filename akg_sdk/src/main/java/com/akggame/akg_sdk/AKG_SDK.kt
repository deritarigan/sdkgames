package com.akggame.akg_sdk

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustConfig
import com.adjust.sdk.LogLevel
import com.akggame.akg_sdk.dao.api.model.FloatingItem
import com.akggame.akg_sdk.dao.api.model.response.CurrentUserResponse
import com.akggame.akg_sdk.presenter.InfoPresenter
import com.akggame.akg_sdk.rx.IView
import com.akggame.akg_sdk.ui.activity.PaymentActivity
import com.akggame.akg_sdk.ui.adapter.FloatingAdapterListener
import com.akggame.akg_sdk.ui.component.FloatingButton
import com.akggame.akg_sdk.ui.component.FloatingItemClickListener
import com.akggame.akg_sdk.ui.dialog.login.LoginDialogFragment
import com.akggame.akg_sdk.ui.dialog.login.RelaunchDialog
import com.akggame.akg_sdk.ui.dialog.menu.*
import com.akggame.akg_sdk.util.CacheUtil
import com.akggame.akg_sdk.util.DeviceUtil
import com.akggame.android.sdk.R

object AKG_SDK : AccountIView {

    private lateinit var customCallback: LoginSDKCallback
    private lateinit var menuCallback: MenuSDKCallback
    //    lateinit var activity: AppCompatActivity
    val SDK_PAYMENT_CODE = 199


//    @JvmStatic
    fun checkIsLogin(context: Context): Boolean {
        return CacheUtil.getPreferenceBoolean(IConfig.SESSION_LOGIN, context)
    }


    fun registerAdjustOnAKG(application: Application) {
        val appToken = "y1t3z228xxj4"
        val environment = AdjustConfig.ENVIRONMENT_SANDBOX
        val config = AdjustConfig(application, appToken, environment)
        config.setLogLevel(LogLevel.VERBOSE); // enable all logs
        Adjust.onCreate(config)
        Log.d("Adjust", "Initiate")

        application.registerActivityLifecycleCallbacks(AdjustLifecycleCallbacks())

    }

    private class AdjustLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
        override fun onActivityStarted(p0: Activity) {

        }

        override fun onActivityDestroyed(p0: Activity) {

        }

        override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {

        }

        override fun onActivityStopped(p0: Activity) {

        }

        override fun onActivityCreated(p0: Activity, p1: Bundle?) {

        }

        override fun onActivityResumed(activity: Activity) {
            Adjust.onResume()
        }

        override fun onActivityPaused(activity: Activity) {
            Adjust.onPause()
        }
    }

    fun setRelauchDialog(activity: AppCompatActivity, menuSDKCallback: MenuSDKCallback) {
        menuCallback = menuSDKCallback
        val dialog =
            RelaunchDialog.newInstance(menuSDKCallback)
        val ftransaction = activity.supportFragmentManager.beginTransaction()
        ftransaction?.addToBackStack("relaunch")
        dialog.show(ftransaction, "relaunch")
    }

    fun setFloatingButton(
        activity: AppCompatActivity,
        floatingButton: FloatingButton,
        context: Context,
        menuSDKCallback: MenuSDKCallback
    ) {
        menuCallback = menuSDKCallback
        val onItemClickListener: FloatingAdapterListener = object : FloatingAdapterListener {
            override fun onItemClick(position: Int, floatingItem: FloatingItem) {
                val contactUsDialog = InfoDialog()
                val checkVersionDialog = CheckVersionDialog()
                val bindAccountDialog = BindAccountDialog.newInstance(
                    activity.supportFragmentManager,
                    floatingButton,
                    this@AKG_SDK,
                    menuSDKCallback
                )
                val logoutDialog = LogoutDialog.newInstance(menuCallback)
                val accountDialog = AccountDialog.newInstance(activity.supportFragmentManager)
                when (position) {
                    0 -> {
                        if (CacheUtil.getPreferenceString(
                                IConfig.LOGIN_TYPE,
                                activity
                            )?.equals(IConfig.LOGIN_GUEST)!!
                        ) {
                            bindAccountDialog.show(activity.supportFragmentManager, "bind account")
                        } else {
                            accountDialog.show(activity.supportFragmentManager, "account")
                        }
                    }
                    1 -> {
                        Toast.makeText(context, "fb", Toast.LENGTH_LONG).show()
                    }
                    2 -> Toast.makeText(context, "eula", Toast.LENGTH_LONG).show()
                    3 -> contactUsDialog.show(activity.supportFragmentManager, "contact us")

                    4 -> {
                        checkVersionDialog.show(activity.supportFragmentManager, "check version")
                    }
                    5 -> {
                        logoutDialog.show(activity.supportFragmentManager, "logout")
                    }
                }
            }
        }

        floatingButton.floatingAdapterListener = onItemClickListener

        floatingButton.circleIcon.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.btn_akg_logo))
        floatingButton.clearAllItems()
        if (CacheUtil.getPreferenceString(IConfig.LOGIN_TYPE, activity)?.equals(IConfig.LOGIN_GUEST)!!) {
            floatingButton.addItem(FloatingItem(ContextCompat.getDrawable(context, R.mipmap.btn_bind_account),null,"Bind Account"))

        } else {
            floatingButton.addItem(FloatingItem(ContextCompat.getDrawable(context, R.mipmap.btn_verify_account),null,"Account Info"))
        }
        floatingButton.addItem(FloatingItem(ContextCompat.getDrawable(context, R.mipmap.btn_fb),null,"FB Fanpage"))
        floatingButton.addItem(FloatingItem(ContextCompat.getDrawable(context, R.mipmap.btn_eula),null,"Eula"))
        floatingButton.addItem(FloatingItem(ContextCompat.getDrawable(context, R.mipmap.btn_contact_us),
            null,"Contact Us"))
        floatingButton.addItem(FloatingItem(ContextCompat.getDrawable(context, R.mipmap.btn_sdk_version),
            null,"SDK Version"))
        floatingButton.addItem(FloatingItem(ContextCompat.getDrawable(context, R.mipmap.btn_log_out),
            null,"Logout"))
        callGetAccount(activity, context)
    }

//    @JvmStatic
    fun onLogin(activity: AppCompatActivity, gameName: String, loginSDKCallback: LoginSDKCallback) {
        CacheUtil.putPreferenceString(IConfig.SESSION_GAME, gameName, activity)
        if (!CacheUtil.getPreferenceBoolean(IConfig.SESSION_LOGIN, activity)) {
            this.customCallback = loginSDKCallback
            val loginDialogFragment =
                LoginDialogFragment.newInstance(activity.supportFragmentManager, customCallback)
            val ftransaction = activity.supportFragmentManager.beginTransaction()
            loginDialogFragment.clearBackStack()
            ftransaction?.addToBackStack("login")
            loginDialogFragment.show(ftransaction, "login")
        } else {
            Toast.makeText(activity, "You already logged in", Toast.LENGTH_LONG).show()
        }
    }

    private fun callGetAccount(activity: AppCompatActivity, context: Context) {
        InfoPresenter(this).onGetCurrentUser(activity, context)
    }

    fun onSDKPayment(activity: AppCompatActivity) {
        val intent = Intent(activity, PaymentActivity::class.java)
        activity.startActivityForResult(intent, SDK_PAYMENT_CODE)
    }

    override fun doOnSuccess(activity: AppCompatActivity, data: CurrentUserResponse) {
        if (CacheUtil.getPreferenceString(IConfig.LOGIN_TYPE, activity) == IConfig.LOGIN_PHONE) {
            data.data?.attributes?.phone_number
            CacheUtil.putPreferenceString(IConfig.SESSION_USERNAME, data.data?.attributes?.phone_number!!, activity)

        } else {
            if (data.data?.attributes?.email != null) {
                CacheUtil.putPreferenceString(
                    IConfig.SESSION_USERNAME, data.data?.attributes?.email!!,
                    activity
                )
            } else {
                CacheUtil.putPreferenceString(
                    IConfig.SESSION_USERNAME, "",
                    activity
                )
            }
        }
        if (data.data?.attributes?.uid != null) {
            CacheUtil.putPreferenceString(
                IConfig.SESSION_UID, data.data?.attributes?.uid!!,
                activity
            )
        }

    }

    override fun doOnError(message: String?) {
    }

    override fun handleError(message: String) {
    }

    override fun handleRetryConnection() {
    }
}