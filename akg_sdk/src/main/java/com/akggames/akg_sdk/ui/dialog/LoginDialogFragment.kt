package com.akggames.akg_sdk.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import com.akggames.android.R
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.FacebookSdk.getApplicationContext
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.content_dialog_login.*
import kotlinx.android.synthetic.main.content_dialog_login.view.*


class LoginDialogFragment : BaseDialogFragment() {

    lateinit var mView: View
    lateinit var callbackManager: CallbackManager
    lateinit var mGoogleSignInClient: GoogleSignInClient

    var mDismissed: Boolean = true
    var mShownByMe = false
    var onViewDestroyed = true

    companion object {
        lateinit var myFragmentManager: FragmentManager

        fun newInstance(mFragmentManager: FragmentManager): LoginDialogFragment {
            val mDialogFragment = LoginDialogFragment()
            this.myFragmentManager = mFragmentManager
            return mDialogFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView =
            LayoutInflater.from(requireActivity() as Context).inflate(R.layout.content_dialog_login, container, false)
        return mView
    }

    override fun onStart() {
        super.onStart()
        mDismissed = false
        mShownByMe = true
        onViewDestroyed = false

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        // Build a GoogleSignInClient with the options specified by gso.

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(requireActivity().application);

        initialize()
    }

    fun initialize() {
        mView.btnLoginFacebook.setOnClickListener {
            fbLoginButton.performClick()
        }

        callbackManager = CallbackManager.Factory.create();
        btnLoginFacebook.setOnClickListener {
            fbLoginButton.performClick()
        }

        btnLoginGoogle.setOnClickListener {
            val signInIntent = mGoogleSignInClient.getSignInIntent()
            ActivityCompat.startActivityForResult(requireActivity(), signInIntent, 101, Bundle())
        }

        fbLoginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                //onSuccess
            }

            override fun onCancel() {
                //Cancel
            }

            override fun onError(error: FacebookException?) {
                //App Code
            }
        })

        mView.btnLoginPhone.setOnClickListener {
            this.dismiss()
//            dismissInternal(false)
            changeToPhoneLogin()
        }
    }
        fun changeToPhoneLogin() {
        val phoneLoginDialogFragment = PhoneLoginDialogFragment.newInstance(myFragmentManager)
        val ftransaction = myFragmentManager.beginTransaction()
        phoneLoginDialogFragment.show(ftransaction, "Phone")
//            phoneLoginDialogFragment.show(myFragmentManager,"Tag")
    }

//    override fun onDismiss(dialog: DialogInterface?) {
//        dismissInternal(true)
//    }

//    internal fun dismissInternal(allowStateLoss: Boolean) {
//
//        if (mDismissed) {
//            return
//        }
//        mDismissed = true
//        mShownByMe = false
////        if (dialog != null) {
////            dialog.dismiss()
////        }
//        onViewDestroyed = true
////        if (mBackStackId >= 0) {
////            fragmentManager!!.popBackStack(
////                mBackStackId,
////                FragmentManager.POP_BACK_STACK_INCLUSIVE
////            )
////            mBackStackId = -1
////        } else {
//        val ft = myFragmentManager!!.beginTransaction()
//        ft.remove(this)
//        if (allowStateLoss) {
//            ft.commitAllowingStateLoss()
//        } else {
//            ft.commit()
//        }
////        }
//    }
//
//

}