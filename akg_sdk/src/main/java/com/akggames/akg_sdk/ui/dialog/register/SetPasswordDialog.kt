package com.akggames.akg_sdk.ui.dialog.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.akggames.akg_sdk.dao.api.model.request.SignUpRequest
import com.akggames.akg_sdk.dao.api.model.response.BaseResponse
import com.akggames.akg_sdk.presenter.RegisterPresenter
import com.akggames.akg_sdk.ui.dialog.BaseDialogFragment
import com.akggames.akg_sdk.ui.dialog.PhoneLoginDialogFragment
import com.akggames.akg_sdk.ui.dialog.SuccessDialogFragment
import com.akggames.akg_sdk.util.DeviceUtil
import com.akggames.android.sdk.R
import kotlinx.android.synthetic.main.content_dialog_input_password.*
import kotlinx.android.synthetic.main.content_dialog_input_password.view.*
import kotlinx.android.synthetic.main.content_dialog_input_password.view.btnNext
import kotlinx.android.synthetic.main.content_dialog_input_password.view.etPassword
import kotlinx.android.synthetic.main.content_dialog_registration.view.*


class SetPasswordDialog : BaseDialogFragment(), SetPasswordIView {


    lateinit var mView: View
    val presenter = RegisterPresenter(this@SetPasswordDialog)
    var phone : String? = ""

    companion object {
         var myFragmentManager: FragmentManager?= null


        fun newInstance(mFragmentManager: FragmentManager?, bundle: Bundle): SetPasswordDialog {
            myFragmentManager = mFragmentManager
            val mDialog = SetPasswordDialog()
            mDialog.arguments = bundle
                return mDialog
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.content_dialog_input_password, container, true)
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

    override fun doOnSuccess(data: BaseResponse) {
        val bundle: Bundle = Bundle()
        bundle.putString("phone",phone)
        val successDialog = SuccessDialogFragment.newInstance(null,bundle)
        successDialog.show(requireFragmentManager(), "Success")
//        this.dismiss()
        customDismiss()
    }

    override fun handleError(message: String) {

    }

    override fun handleRetryConnection() {

    }

    fun initialize() {
        val model = SignUpRequest()
        mView.btnNext.setOnClickListener {
            model.phone_model = "Samsung"
            model.auth_provider = "akg"
            model.game_provider = "mobile-legend"
            model.device_id = DeviceUtil().getImei(requireActivity())
            model.operating_system = "android"
            model.password = etPassword.text.toString()
            model.phone_number = phone
            presenter.onSignUp(model, requireActivity())
            //            this.dismiss()
        }
    }
}