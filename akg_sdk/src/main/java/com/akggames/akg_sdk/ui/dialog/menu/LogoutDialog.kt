package com.akggames.akg_sdk.ui.dialog.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.akggames.akg_sdk.IConfig
import com.akggames.akg_sdk.dao.SocmedDao
import com.akggames.akg_sdk.rx.IView
import com.akggames.akg_sdk.ui.dialog.BaseDialogFragment
import com.akggames.akg_sdk.util.CacheUtil
import com.akggames.android.sdk.R
import kotlinx.android.synthetic.main.content_dialog_logout.*
import kotlinx.android.synthetic.main.content_dialog_logout.view.*
import kotlinx.android.synthetic.main.content_dialog_logout.view.btnLogout

class LogoutDialog: BaseDialogFragment(),IView {
    lateinit var mView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.content_dialog_logout, container, true)
        return mView
    }

    override fun onStart() {
        super.onStart()
        initialize()
    }

    fun initialize(){
        mView.ivClose.setOnClickListener {
            this.dismiss()
        }

        btnLogout.setOnClickListener {
            val loginType = CacheUtil.getPreferenceString(IConfig.LOGIN_TYPE,requireActivity())
            when(loginType){
                IConfig.LOGIN_PHONE-> SocmedDao.logoutPhone(requireActivity(),this)
                IConfig.LOGIN_GOOGLE-> SocmedDao.logoutGoogle(requireActivity(),this)
                IConfig.LOGIN_FACEBOOK-> SocmedDao.logoutFacebook(requireActivity(),this)
            }
        }
    }
}