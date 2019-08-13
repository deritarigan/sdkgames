package com.akggames.akg_sdk.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.akggames.akg_sdk.dao.api.model.request.PhoneAuthRequest
import com.akggames.akg_sdk.presenter.AuthPresenter
import com.akggames.akg_sdk.rx.IView
import com.akggames.akg_sdk.ui.dialog.forget.ForgetDialog
import com.akggames.akg_sdk.ui.dialog.register.OTPDialog
import com.akggames.akg_sdk.util.DeviceUtil
import com.akggames.android.sdk.R
import kotlinx.android.synthetic.main.content_dialog_login_phone.*
import kotlinx.android.synthetic.main.content_dialog_login_phone.view.*

class PhoneLoginDialogFragment : BaseDialogFragment(), IView {
    override fun handleError(message: String) {
    }

    override fun handleRetryConnection() {
    }

    companion object {
        lateinit var myFragmentManager: FragmentManager


        fun newInstance(mFragmentManager: FragmentManager): PhoneLoginDialogFragment {
            myFragmentManager = mFragmentManager
            return PhoneLoginDialogFragment()
        }

    }

    private lateinit var mView: View
    val presenter = AuthPresenter(this@PhoneLoginDialogFragment)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.content_dialog_login_phone, container, true)
        return mView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val style = DialogFragment.STYLE_NO_FRAME
        setStyle(style, theme)
    }

    override fun onStart() {
        super.onStart()
        initView()
    }

    fun initView() {
        mView.tvCreateAccount.setOnClickListener {
            val otpDialog = OTPDialog()
            val ftransaction =fragmentManager?.beginTransaction()
            ftransaction?.addToBackStack("dialog")
            otpDialog.show(requireFragmentManager(), "Registration")
            this.customDismiss()
//            this.dismiss()
        }
        mView.btnLoginPhone.setOnClickListener {
            val phoneAuthRequest = PhoneAuthRequest()
            phoneAuthRequest.phone_number = etPhoneNumber.text.toString()
            phoneAuthRequest.password = etOtpCode.text.toString()
            phoneAuthRequest.auth_provider = "akg"
            phoneAuthRequest.game_provider = "mobile-legends"
            phoneAuthRequest.device_id = DeviceUtil().getImei(requireActivity())
            phoneAuthRequest.phone_model = "Xiaomi"
            phoneAuthRequest.operating_system = "Android"
            presenter.phoneLogin(phoneAuthRequest, requireActivity())
        }
        mView.tvForgotPassword.setOnClickListener{
            val forgetDialog = ForgetDialog()
            forgetDialog.show(requireFragmentManager(), "Forget")
        }
    }
}