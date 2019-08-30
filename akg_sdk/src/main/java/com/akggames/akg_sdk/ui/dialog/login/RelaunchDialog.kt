package com.akggames.akg_sdk.ui.dialog.login

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.akggames.akg_sdk.IConfig
import com.akggames.akg_sdk.MenuSDKCallback
import com.akggames.akg_sdk.dao.SocmedDao
import com.akggames.akg_sdk.presenter.LogoutPresenter
import com.akggames.akg_sdk.ui.dialog.BaseDialogFragment
import com.akggames.akg_sdk.ui.dialog.menu.LogoutIView
import com.akggames.akg_sdk.util.CacheUtil
import com.akggames.android.sdk.R
import kotlinx.android.synthetic.main.content_dialog_relaunch.*
import kotlinx.android.synthetic.main.content_dialog_relaunch.view.*


class RelaunchDialog() : BaseDialogFragment(), LogoutIView {


    lateinit var mView: View
    private val presenter = LogoutPresenter(this)

    companion object {
        lateinit var menuSDKCallback: MenuSDKCallback
        fun newInstance(callback: MenuSDKCallback): RelaunchDialog {
            val mDialogFragment = RelaunchDialog()
            menuSDKCallback = callback

            return mDialogFragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.content_dialog_relaunch, container, true)
        return mView
    }

    override fun onStart() {
        super.onStart()
        initialize()
    }

    override fun doSuccess() {
        this.dismiss()
        menuSDKCallback.onLogout()
    }

    override fun doError() {
        Toast.makeText(requireActivity(), "Error logout", Toast.LENGTH_LONG).show()
    }

    fun initialize() {
        tvPhoneNumber.text = "Welcome " + CacheUtil.getPreferenceString(IConfig.SESSION_USERNAME, requireActivity())
        tvUID.text = "UID : "+CacheUtil.getPreferenceString(IConfig.SESSION_UID, requireActivity())
        val countDown = object : CountDownTimer(4000, 1000) {
            override fun onFinish() {
                mView.btnBindFacebook.text = "Continue in 0s"
                mView.btnBindFacebook.performClick()
            }

            override fun onTick(millisUntilFinished: Long) {
                mView.btnBindFacebook.text = "Continue in " + (millisUntilFinished / 1000).toString() + "s"
            }
        }
        countDown.start()

        mView.btnBindFacebook.setOnClickListener {
            dismiss()
        }

        mView.btnBindGoogle.setOnClickListener {
            val loginType = CacheUtil.getPreferenceString(IConfig.LOGIN_TYPE, requireActivity())
            when (loginType) {
                IConfig.LOGIN_PHONE -> SocmedDao.logoutPhone(requireActivity(), this, presenter)
                IConfig.LOGIN_GUEST -> SocmedDao.logoutGuest(requireActivity(), this, presenter)
                IConfig.LOGIN_GOOGLE -> SocmedDao.logoutGoogle(requireActivity(), this, presenter)
                IConfig.LOGIN_FACEBOOK -> SocmedDao.logoutFacebook(requireActivity(), this, presenter)
            }
        }
    }
}