package com.akggames.akg_sdk.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.akggames.android.R
import kotlinx.android.synthetic.main.content_dialog_success.view.*

class SuccessDialogFragment : BaseDialogFragment() {

    lateinit var mView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.content_dialog_success, container, true)

        return mView
    }

    override fun onStart() {
        super.onStart()
        initialize()
    }

    fun initialize(){
        mView.btnNext.setOnClickListener {
            this.dismiss()
        }
    }
}