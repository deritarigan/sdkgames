package com.akggames.akg_sdk.ui.dialog

import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.akggames.akg_sdk.rx.IView
import com.akggames.akg_sdk.ui.dialog.login.LoginDialogFragment
import com.akggames.android.sdk.R

open class BaseDialogFragment : DialogFragment(), IView {
    override fun handleError(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handleRetryConnection() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

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
                this.dialog?.window?.setBackgroundDrawable(ColorDrawable(context?.resources!!.getColor(R.color.transparent,null)))
            }
        }
    }

    fun moveToAnotherDialog() {
        fragmentManager?.beginTransaction()
    }

    protected fun changeFragment(container: Int, fragment: Fragment) {
        fragmentManager?.beginTransaction()
            ?.addToBackStack(null)
            ?.replace(container, fragment, fragment.javaClass.simpleName)
            ?.commit()
    }

    fun showFragment(fragment: Fragment?, fragmentResourceID: Int) {
    }

    fun customDismiss() {
        val ft = fragmentManager!!.beginTransaction()
        ft.remove(this)
        ft.commit()
    }



     fun onBackPressed() {
        var stackEntry = fragmentManager?.backStackEntryCount
        if (stackEntry != null) {
            if (stackEntry > 1) {
//                val dialog = fragmentManager?.getBackStackEntryAt(stackEntry-2) as BaseDialogFragment
//                dialog?.show(fragmentManager?.beginTransaction(),"tes")
                customDismiss()
            }
        }
    }
}