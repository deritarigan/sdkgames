package com.akggames.akg_sdk.ui.dialog.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.akggames.akg_sdk.IConfig
import com.akggames.akg_sdk.dao.api.model.response.CurrentUserResponse
import com.akggames.akg_sdk.presenter.InfoPresenter
import com.akggames.akg_sdk.ui.dialog.BaseDialogFragment
import com.akggames.akg_sdk.util.CacheUtil
import com.akggames.android.sdk.R
import kotlinx.android.synthetic.main.content_dialog_account.*
import kotlinx.android.synthetic.main.content_dialog_account.view.*

class AccountDialog(fm: FragmentManager?) : BaseDialogFragment(), AccountIView {

    lateinit var mView: View
    val presenter: InfoPresenter = InfoPresenter(this)

    companion object {
        fun newInstance(fm: FragmentManager?): AccountDialog {
            return AccountDialog(fm)
        }
    }

    init {
        myFragmentManager = fm
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.content_dialog_account, container, true)
        return mView
    }

    override fun onStart() {
        super.onStart()
        initialize()
        presenter.onGetCurrentUser(requireActivity())
    }

    override fun doOnSuccess(data: CurrentUserResponse) {
        if (CacheUtil.getPreferenceString(IConfig.LOGIN_TYPE, requireActivity()) == IConfig.LOGIN_PHONE) {
            etOldPassword.text = data.data?.attributes?.phone_number
        } else {
            etOldPassword.text = data.data?.attributes?.email
        }
        etNewPassword.text = data.data?.attributes?.uid
    }

    override fun doOnError(message: String?) {
    }

    fun initialize() {
        mView.ivClose.setOnClickListener {
           customDismiss()
            clearBackStack()
        }

        mView.tvChangePassword.setOnClickListener {
            val changePasswordDialog = ChangePasswordDialog.newInstance(myFragmentManager)
            changePasswordDialog.show(myFragmentManager, "change password")
            customDismiss()
        }
    }
}