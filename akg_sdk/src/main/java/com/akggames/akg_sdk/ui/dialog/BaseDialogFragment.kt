package com.akggames.akg_sdk.ui.dialog

import android.content.DialogInterface
import android.content.res.Configuration
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.akggames.akg_sdk.rx.IView
import com.akggames.android.sdk.R



open class BaseDialogFragment() : DialogFragment(), IView {
    override fun handleError(message: String) {
    }

    var myFragmentManager: FragmentManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val style = STYLE_NO_FRAME
        setStyle(style, theme)
    }


    override fun onStart() {
        super.onStart()
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            this.dialog?.window?.setLayout(width, height)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                this.dialog?.window?.setBackgroundDrawable(
                    ColorDrawable(
                        context?.resources!!.getColor(
                            R.color.transparent,
                            null
                        )
                    )
                )
            }
        }

        dialog.setOnKeyListener(object : View.OnKeyListener, DialogInterface.OnKeyListener {
            override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
                return false
            }

            override fun onKey(p0: DialogInterface?, keyCode: Int, event: KeyEvent?): Boolean {
                if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                    onBackPressed()
                    return true
                } else {
                    return false
                }
            }
        })
    }


    override fun handleRetryConnection() {
    }

    fun customDismiss() {
        val ft = myFragmentManager!!.beginTransaction()
        ft.remove(this)
        ft.commit()
    }

    fun clearBackStack(){
        val count = myFragmentManager?.getBackStackEntryCount()!!
        for (i in 0 until count) {
            myFragmentManager?.popBackStack()
        }
    }
    fun onRelauch(){
//        var backStackSize = myFragmentManager?.backStackEntryCount
//        if (backStackSize != null) {
//            if (backStackSize > 0) {
//                var backEntry = myFragmentManager?.getBackStackEntryAt(backStackSize-1)
//                customDismiss()
//                myFragmentManager?.popBackStack(this.tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
//                val mDialog =
//                    myFragmentManager?.findFragmentByTag(backEntry?.name) as BaseDialogFragment
//                if (myFragmentManager != null) {
//                    myFragmentManager!!.beginTransaction().remove(mDialog)
//                    mDialog.show(myFragmentManager!!.beginTransaction(), backEntry?.name)
//                }
//            }
//        }
//        customDismiss()
        clearBackStack()
    }
    fun onBackPressed() {
        var backStackSize = myFragmentManager?.backStackEntryCount

        if (backStackSize != null) {
            if (backStackSize > 1) {
                var backEntry = myFragmentManager?.getBackStackEntryAt(backStackSize - 2)
                customDismiss()
                myFragmentManager?.popBackStack(this.tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                val mDialog =
                    myFragmentManager?.findFragmentByTag(backEntry?.name) as BaseDialogFragment
                if (myFragmentManager != null) {
                    myFragmentManager!!.beginTransaction().remove(mDialog)
                    mDialog.show(myFragmentManager!!.beginTransaction(), null)
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        onRelauch()
    }
}