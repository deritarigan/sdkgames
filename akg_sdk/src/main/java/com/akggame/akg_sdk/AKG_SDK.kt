package com.akggame.akg_sdk

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.akggame.akg_sdk.dao.AkgDao
import com.akggame.akg_sdk.ui.activity.PaymentActivity
import com.akggame.akg_sdk.ui.component.FloatingButton
import com.akggame.akg_sdk.util.CacheUtil
import com.android.billingclient.api.SkuDetails
import com.akggame.akg_sdk.dao.*

object AKG_SDK {

    private lateinit var menuCallback: MenuSDKCallback
    //    lateinit var activity: AppCompatActivity
    private lateinit var mFloatingButton: FloatingButton
    private val AkgDao = AkgDao()
    const val SDK_PAYMENT_CODE = 199
    const val SDK_PAYMENT_DATA = "akg_purchase_data"
    const val LOGIN_GOOGLE = "loginGoogle"
    const val LOGIN_FACEBOOK = "loginFacebook"
    const val LOGIN_PHONE = "loginPhone"

    fun checkIsLogin(context: Context): Boolean {
        return CacheUtil.getPreferenceBoolean(IConfig.SESSION_LOGIN, context)
    }

    fun getMenuCallback(): MenuSDKCallback {
        return menuCallback
    }
    fun registerAdjustOnAKG(gameProvider: String, application: Application) {
//        AkgDao.registerAdjust(gameProvider,application)
    }

    fun getProducts(application: Application, context: Context, callback: ProductSDKCallback) {
        AkgDao.getProducts(application,context,callback)
    }

    fun callBannerDialog(activity: AppCompatActivity) {
        AkgDao.callBannerDialog(activity)
    }

    fun launchBilling(activity: Activity, skuDetails: SkuDetails, callback: PurchaseSDKCallback) {
        AkgDao.launchBilling(activity,skuDetails,callback)
    }

    fun setRelauchDialog(activity: AppCompatActivity,callback : RelaunchSDKCallback) {
        AkgDao.callRelaunchDialog(activity, callback)
    }

    fun resetFloatingButton(activity: AppCompatActivity) {
        setFloatingButton(activity, mFloatingButton, activity, menuCallback)
    }

    fun setFloatingButton(
        activity: AppCompatActivity,
        floatingButton: FloatingButton,
        context: Context,
        menuSDKCallback: MenuSDKCallback
    ) {
        menuCallback = menuSDKCallback
        AkgDao.setFloatingButtonListener(activity,floatingButton,context,menuSDKCallback)
        mFloatingButton = AkgDao.setFloatingButtonItem(floatingButton,activity)
    }

    fun onLogin(activity: AppCompatActivity, gameName: String, loginSDKCallback: LoginSDKCallback) {
        AkgDao.callLoginDialog(activity,gameName,loginSDKCallback)
    }

    fun onSDKPayment(activity: AppCompatActivity) {
        val intent = Intent(activity, PaymentActivity::class.java)
        activity.startActivityForResult(intent, SDK_PAYMENT_CODE)
    }
}