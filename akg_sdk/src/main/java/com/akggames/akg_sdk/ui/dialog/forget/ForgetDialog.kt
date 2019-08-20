package com.akggames.akg_sdk.ui.dialog.forget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.akggames.akg_sdk.animateScale
import com.akggames.akg_sdk.beginDelayedTransition
import com.akggames.akg_sdk.dao.api.model.request.SendOtpRequest
import com.akggames.akg_sdk.dao.api.model.response.BaseResponse
import com.akggames.akg_sdk.doAfterAnimate
import com.akggames.akg_sdk.presenter.RegisterPresenter
import com.akggames.akg_sdk.ui.dialog.BaseDialogFragment
import com.akggames.akg_sdk.ui.dialog.register.OTPIView
import com.akggames.akg_sdk.ui.dialog.register.SetPasswordDialog
import com.akggames.android.sdk.R
import kotlinx.android.synthetic.main.content_dialog_forgot.*
import kotlinx.android.synthetic.main.content_dialog_forgot.view.*
import kotlinx.android.synthetic.main.content_dialog_forgot.view.btnNext
import kotlinx.android.synthetic.main.content_dialog_forgot.view.etOtpCode
import kotlinx.android.synthetic.main.content_dialog_forgot.view.etPhoneNumber
import kotlinx.android.synthetic.main.content_dialog_registration.view.*

class ForgetDialog(fm:FragmentManager?) : BaseDialogFragment(), OTPIView {

    companion object {
        fun newInstance(fm: FragmentManager?): ForgetDialog {
            return ForgetDialog(fm)
        }
    }

    init {
        myFragmentManager = fm
    }

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
        clOtp.animateScale(1.0f, 1.0f, 350L / 2)
            .doAfterAnimate {
                clOtp.beginDelayedTransition(350L)
                clOtp.visibility = View.VISIBLE
            }
    }

    override fun doOnSuccessCheck(data: BaseResponse) {
        var bundle = Bundle()
        bundle.putString("phone","+62"+mView.etPhoneNumber.text.toString())
        val updatePassword = UpdatePasswordDialog.newInstance(myFragmentManager,bundle)
        val ftransaction = myFragmentManager?.beginTransaction()
        ftransaction?.addToBackStack("update password")
        updatePassword.show(ftransaction, "update password")
        customDismiss()
    }

    fun initialize() {
        sendOtpRequest.auth_provider = "akg"
        sendOtpRequest.game_provider = "mobile-legends"
        sendOtpRequest.otp_type = "forgot_password"

        mView.btnNext.setOnClickListener {
            if (mView.etPhoneNumber.text.isNotEmpty()) {
                val presenter = RegisterPresenter(this@ForgetDialog)
                sendOtpRequest.phone_number = "+62"+mView.etPhoneNumber.text.toString()
                if (!isGenerateOTP) {
                    presenter.sendOtp(sendOtpRequest, requireActivity())
                    isGenerateOTP = true
                } else {
                    sendOtpRequest.otp_code = mView.etOtpCode.text.toString()
                    presenter.checkOtp(sendOtpRequest, requireActivity())
                }
            } else {
                Toast.makeText(requireActivity(), "field cannot be empty", Toast.LENGTH_LONG).show()
            }
        }
    }
}