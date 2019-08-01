package com.akggames.akg_sdk.ui.dialog

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.akggames.akg_sdk.ui.dialog.register.OTPDialog
import com.akggames.android.R
import kotlinx.android.synthetic.main.content_dialog_registration.view.*

class PhoneLoginDialogFragment : DialogFragment() {

    companion object {
        lateinit var myFragmentManager: FragmentManager

        fun newInstance(mFragmentManager: FragmentManager): PhoneLoginDialogFragment {
            myFragmentManager = mFragmentManager
            return PhoneLoginDialogFragment()
        }

    }

    private lateinit var mView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.content_dialog_login_phone, container, false)
        return mView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val style = DialogFragment.STYLE_NO_FRAME
        val theme = R.style.CoconutDialogScreen
        setStyle(style, theme)
    }

    override fun onStart() {
        super.onStart()
        initView()
    }

    fun initView() {
        mView.tvCreateAccount.setOnClickListener {
            val otpDialog = OTPDialog()
            otpDialog.show(requireFragmentManager(), "Registration")
            this.dismiss()
        }
    }
}