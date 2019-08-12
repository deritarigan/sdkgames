package com.akggames.akg_sdk.ui.dialog.forget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.akggames.akg_sdk.dao.api.model.request.SendOtpRequest
import com.akggames.akg_sdk.dao.api.model.response.BaseResponse
import com.akggames.akg_sdk.presenter.RegisterPresenter
import com.akggames.akg_sdk.ui.dialog.BaseDialogFragment
import com.akggames.akg_sdk.ui.dialog.register.SetPasswordDialog
import com.akggames.android.sdk.R
import kotlinx.android.synthetic.main.content_dialog_registration.view.*

class ForgetDialog : BaseDialogFragment(), ForgetIView {


    lateinit var mView: View
    var isGenerateOTP: Boolean = false
    var sendOtpRequest: SendOtpRequest = SendOtpRequest()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.content_dialog_forgot, container, true)
        return mView
    }

    override fun onStart() {
        super.onStart()
        initialize()
    }

    override fun doOnSuccessGenerate(data: BaseResponse) {
        isGenerateOTP = true
    }

    override fun doOnSuccessCheck(data: BaseResponse) {
        val setPasswordDialog = SetPasswordDialog()
        setPasswordDialog.show(requireFragmentManager(), "Set Password")
        this.dismiss()
    }

    fun initialize() {
        sendOtpRequest.auth_provider = "akg"
        sendOtpRequest.game_provider = "mobile-legend"
        sendOtpRequest.otp_type = "forgot_password"

        mView.btnNext.setOnClickListener {
            val presenter = RegisterPresenter(this@ForgetDialog)
            sendOtpRequest.phone_number = mView.etPhoneNumber.text.toString()
            if (!isGenerateOTP) {
                presenter.sendOtp(sendOtpRequest, requireActivity())
                isGenerateOTP = true
            } else {
                sendOtpRequest.otp_code = mView.etOtpCode.text.toString()
                presenter.checkOtp(sendOtpRequest, requireActivity())
            }
        }
    }
}