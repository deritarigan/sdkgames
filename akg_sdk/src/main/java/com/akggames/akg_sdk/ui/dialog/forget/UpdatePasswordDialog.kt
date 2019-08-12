package com.akggames.akg_sdk.ui.dialog.forget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.akggames.akg_sdk.dao.api.model.request.UpdatePasswordRequest
import com.akggames.akg_sdk.dao.api.model.response.BaseResponse
import com.akggames.akg_sdk.presenter.RegisterPresenter
import com.akggames.akg_sdk.ui.dialog.BaseDialogFragment
import com.akggames.akg_sdk.ui.dialog.SuccessDialogFragment
import com.akggames.android.sdk.R
import kotlinx.android.synthetic.main.content_dialog_input_password.*
import kotlinx.android.synthetic.main.content_dialog_input_password.view.*

class UpdatePasswordDialog:BaseDialogFragment(),UpdatePasswordIView {

    lateinit var mView: View
    val presenter = RegisterPresenter(this@UpdatePasswordDialog)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.content_dialog_input_password, container, true)
        return mView
    }

    override fun onStart() {
        super.onStart()
        initialize()
    }

    override fun doOnSuccess(data: BaseResponse) {
        val successDialog = SuccessDialogFragment()
        successDialog.show(requireFragmentManager(), "Success")
        this.dismiss()
    }

    fun initialize() {
        val model = UpdatePasswordRequest()
        mView.btnNext.setOnClickListener {
            model.auth_provider = "akg"
            model.game_provider = "mobile-legend"
            model.password = etPassword.text.toString()
            model.phone_number = "087708180002"
            presenter.onUpdatePassword(model,requireActivity())
            //            this.dismiss()
        }
    }
}