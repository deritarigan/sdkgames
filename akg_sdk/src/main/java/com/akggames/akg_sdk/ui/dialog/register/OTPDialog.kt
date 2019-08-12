package com.akggames.akg_sdk.ui.dialog.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.akggames.akg_sdk.dao.api.model.request.SendOtpRequest
import com.akggames.akg_sdk.dao.api.model.response.BaseResponse
import com.akggames.akg_sdk.presenter.RegisterPresenter
import com.akggames.akg_sdk.rx.IView
import com.akggames.akg_sdk.ui.dialog.BaseDialogFragment
import com.akggames.android.R
import kotlinx.android.synthetic.main.content_dialog_registration.view.*

class OTPDialog : BaseDialogFragment(), OTPIView {
    override fun doOnSuccessGenerate(data: BaseResponse) {
        isGenerateOTP = true
    }

    override fun doOnSuccessCheck(data: BaseResponse) {
        var bundle: Bundle = Bundle()
        bundle.putString("phone",mView.etPhoneNumber.text.toString())
        val setPasswordDialog = SetPasswordDialog.newInstance(fragmentManager,bundle)
        val ftransaction =fragmentManager?.beginTransaction()
        ftransaction?.addToBackStack("dialog")
        setPasswordDialog.show(requireFragmentManager(), "Set Password")
//        this.dismiss()
        customDismiss()
    }

    override fun handleError(message: String) {

    }

    override fun handleRetryConnection() {

    }

    lateinit var mView: View
    var isGenerateOTP: Boolean = false
    var sendOtpRequest: SendOtpRequest = SendOtpRequest()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.content_dialog_registration, container, true)
        return mView
    }

    override fun onStart() {
        super.onStart()
        initialize()
    }

    fun initialize() {
        sendOtpRequest.auth_provider = "akg"
        sendOtpRequest.game_provider = "mobile-legend"
        sendOtpRequest.otp_type = "registration"

        mView.btnNext.setOnClickListener {
            val presenter = RegisterPresenter(this@OTPDialog)
            sendOtpRequest.phone_number = mView.etPhoneNumber.text.toString()
            if (!isGenerateOTP) {
                presenter.sendOtp(sendOtpRequest, requireActivity())
                isGenerateOTP=true
            }else{
                sendOtpRequest.otp_code = mView.etOtpCode.text.toString()
                presenter.checkOtp(sendOtpRequest,requireActivity())
            }
        }
    }
}