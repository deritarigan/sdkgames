package com.akggames.akg_sdk.ui.dialog.menu.binding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.akggames.akg_sdk.ui.dialog.BaseDialogFragment
import com.akggames.android.sdk.R
import kotlinx.android.synthetic.main.content_dialog_verify.*
import kotlinx.android.synthetic.main.content_dialog_verify.view.*
import kotlinx.android.synthetic.main.content_dialog_verify.view.btnNext

class VerifyAccountDialog(fm:FragmentManager?) :BaseDialogFragment(){

    lateinit var mView: View

    companion object {
        fun newInstance(fm: FragmentManager?): VerifyAccountDialog {
            return VerifyAccountDialog(fm)
        }
    }

    init {
        myFragmentManager = fm
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.content_dialog_verify, container, true)
        return mView
    }

    override fun onStart() {
        super.onStart()
        initialize()
    }

    fun initialize() {
        mView.ivClose.setOnClickListener {
            customDismiss()
            clearBackStack()
        }

        btnNext.setOnClickListener {
            val verification = VerificationDialog.newInstance(myFragmentManager)
            val ftransaction =myFragmentManager?.beginTransaction()
            ftransaction?.addToBackStack("verification")
            verification.show(ftransaction, "verification")
            customDismiss()
        }
    }
}