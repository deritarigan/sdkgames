package com.akggames.akg_sdk.ui.dialog.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.transition.TransitionManager.beginDelayedTransition
import com.akggames.akg_sdk.animateScale
import com.akggames.akg_sdk.beginDelayedTransition
import com.akggames.akg_sdk.dao.api.model.request.SendOtpRequest
import com.akggames.akg_sdk.dao.api.model.response.BaseResponse
import com.akggames.akg_sdk.doAfterAnimate
import com.akggames.akg_sdk.presenter.RegisterPresenter
import com.akggames.akg_sdk.rx.IView
import com.akggames.akg_sdk.ui.dialog.BaseDialogFragment
import com.akggames.android.sdk.R
import kotlinx.android.synthetic.main.content_dialog_registration.*
import kotlinx.android.synthetic.main.content_dialog_registration.view.*
import kotlinx.android.synthetic.main.content_dialog_registration.view.clOtp

class OTPDialog(fm:FragmentManager?) : BaseDialogFragment(), OTPIView {

    companion object {
        fun newInstance(fm: FragmentManager?): OTPDialog {
            return OTPDialog(fm)
        }
    }

    init {
        myFragmentManager = fm
    }

    override fun doOnSuccessGenerate(data: BaseResponse) {
        isGenerateOTP = true
        clOtp.animateScale(1.0f, 1.0f, 350L / 2)
            .doAfterAnimate {  clOtp.beginDelayedTransition(350L)
                clOtp.visibility = View.VISIBLE
            }
    }

    override fun doOnSuccessCheck(data: BaseResponse) {
        var bundle = Bundle()
        bundle.putString("phone","0"+mView.etPhoneNumber.text.toString())
        val setPasswordDialog = SetPasswordDialog.newInstance(myFragmentManager,bundle)
        val ftransaction =myFragmentManager?.beginTransaction()
        ftransaction?.addToBackStack("set password")
        setPasswordDialog.show(ftransaction, "set password")
        customDismiss()
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
        sendOtpRequest.game_provider = "mobile-legends"
        sendOtpRequest.otp_type = "registration"

        mView.btnNext.setOnClickListener {
            if(mView.etPhoneNumber.text.isNotEmpty()){
                val presenter = RegisterPresenter(this@OTPDialog)
                sendOtpRequest.phone_number = "0"+mView.etPhoneNumber.text.toString()
                if (!isGenerateOTP) {
                    presenter.sendOtp(sendOtpRequest, requireActivity())
                }else{
                    sendOtpRequest.otp_code = mView.etOtpCode.text.toString()
                    presenter.checkOtp(sendOtpRequest,requireActivity())
                }
            }else{
                Toast.makeText(requireActivity(),"field cannot be empty",Toast.LENGTH_LONG).show()
            }
        }
    }
}