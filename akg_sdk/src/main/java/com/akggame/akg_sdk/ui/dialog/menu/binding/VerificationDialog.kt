package com.akggame.akg_sdk.ui.dialog.menu.binding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.akggame.akg_sdk.ui.dialog.BaseDialogFragment
import com.akggame.android.sdk.R
import kotlinx.android.synthetic.main.content_dialog_verify.view.*

class VerificationDialog() :BaseDialogFragment() {
    lateinit var mView: View
    var phone: String? = ""

    companion object {
        fun newInstance(mFragmentManager: FragmentManager?, bundle: Bundle): VerificationDialog {
            val mDialog = VerificationDialog(mFragmentManager)
            mDialog.arguments = bundle
            return mDialog
        }
    }

    constructor(fm: FragmentManager?) : this() {
        myFragmentManager = fm
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.content_dialog_verification, container, true)
        return mView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            phone = bundle.getString("phone")
        }
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
    }
}