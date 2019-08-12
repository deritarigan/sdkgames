package com.akggames.akg_sdk.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.akggames.akg_sdk.ui.dialog.register.SetPasswordDialog
import com.akggames.android.R
import kotlinx.android.synthetic.main.content_dialog_success.view.*

class SuccessDialogFragment : BaseDialogFragment() {

    lateinit var mView: View
    var phone : String? = ""


    companion object {
        var myFragmentManager: FragmentManager? = null


        fun newInstance(mFragmentManager: FragmentManager?, bundle: Bundle): SuccessDialogFragment {
            myFragmentManager = mFragmentManager
            val mDialog = SuccessDialogFragment()
            mDialog.arguments = bundle
            return mDialog
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.content_dialog_success, container, true)

        return mView
    }

    override fun onStart() {
        super.onStart()
        val bundle = this.arguments
        if (bundle != null) {
            phone = bundle.getString("phone")
        }
        initialize()
    }

    fun initialize() {
        mView.btnNext.setOnClickListener {
            this.dismiss()
        }
        mView.tvDesc.setText("Your account with number " + phone + " already been made")

    }
}