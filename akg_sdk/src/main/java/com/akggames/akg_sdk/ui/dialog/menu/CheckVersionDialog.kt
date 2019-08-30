package com.akggames.akg_sdk.ui.dialog.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.akggames.akg_sdk.ui.dialog.BaseDialogFragment
import com.akggames.android.sdk.R
import kotlinx.android.synthetic.main.content_dialog_version.view.*
import android.content.pm.PackageManager
import android.R.attr.versionName
import android.content.pm.PackageInfo
import com.akggames.android.sdk.BuildConfig
import com.fasterxml.jackson.databind.util.ClassUtil.getPackageName


class CheckVersionDialog:BaseDialogFragment() {

    lateinit var mView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.content_dialog_version, container, true)
        return mView
    }

    override fun onStart() {
        super.onStart()
        initialize()
    }

    fun initialize(){
        mView.ivClose.setOnClickListener {
            this.dismiss()
        }

        mView.tvVersion.text = resources.getString(R.string.check_update_desc,BuildConfig.VERSION_NAME)
    }
}