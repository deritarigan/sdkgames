package com.akggame.akg_sdk.ui.dialog.menu.binding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.akggame.akg_sdk.ui.dialog.BaseDialogFragment
import com.akggame.android.sdk.R
import kotlinx.android.synthetic.main.content_dialog_verify.view.*

class VerificationDialog(fm:FragmentManager?) :BaseDialogFragment() {
    lateinit var mView: View

    companion object {
        fun newInstance(fm: FragmentManager?): VerificationDialog {
            return VerificationDialog(fm)
        }
    }

    init {
        myFragmentManager = fm
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.content_dialog_verification, container, true)
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
    }
}