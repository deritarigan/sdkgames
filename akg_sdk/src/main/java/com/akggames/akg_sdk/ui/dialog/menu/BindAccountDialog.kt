package com.akggames.akg_sdk.ui.dialog.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.akggames.akg_sdk.presenter.InfoPresenter
import com.akggames.akg_sdk.ui.dialog.BaseDialogFragment
import com.akggames.akg_sdk.ui.dialog.menu.binding.VerifyAccountDialog
import com.akggames.android.sdk.R
import kotlinx.android.synthetic.main.content_dialog_bind_account.*
import kotlinx.android.synthetic.main.content_dialog_bind_account.view.*
import kotlinx.android.synthetic.main.content_dialog_bind_account.view.btnBindingPhone

class BindAccountDialog(fm:FragmentManager?) : BaseDialogFragment() {

    lateinit var mView: View

    companion object {
        fun newInstance(fm: FragmentManager?): BindAccountDialog {
            return BindAccountDialog(fm)
        }
    }

    init {
        myFragmentManager = fm
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.content_dialog_bind_account, container, true)
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

        btnBindingPhone.setOnClickListener {
            val verifyDialog = VerifyAccountDialog.newInstance(myFragmentManager)
            val ftransaction =myFragmentManager?.beginTransaction()
            ftransaction?.addToBackStack("verify account")
            verifyDialog.show(ftransaction, "verify account")
            customDismiss()
        }

    }
}