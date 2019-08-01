package com.akggames.akg_sdk.ui.dialog

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.akggames.android.R

open class BaseDialogFragment : DialogFragment() {

    companion object {
        lateinit var myFragmentManager: FragmentManager

        fun newInstance(mFragmentManager: FragmentManager): LoginDialogFragment {
            val mDialogFragment = LoginDialogFragment()
            this.myFragmentManager = mFragmentManager
            return mDialogFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val style = DialogFragment.STYLE_NO_FRAME
        val theme = R.style.CoconutDialogScreen
        setStyle(style, theme)
    }

    override fun onStart() {
        super.onStart()
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT

            dialog.window?.setLayout(width, height)
            dialog.window?.setBackgroundDrawable(ColorDrawable(context?.resources!!.getColor(R.color.transparent)))
        }
    }

//    fun changeToPhoneLogin(mFragmentManager: FragmentManager,dialogFragment: BaseDialogFragment) {
//        val dialogFragment = BaseDialogFragment.newInstance(mFragmentManager)
//        val ftransaction = mFragmentManager.beginTransaction()
////        phoneLoginDialogFragment.show(ftransaction, "Phone")
//        dialogFragment.show(mFragmentManager,"Tag")
//    }
}