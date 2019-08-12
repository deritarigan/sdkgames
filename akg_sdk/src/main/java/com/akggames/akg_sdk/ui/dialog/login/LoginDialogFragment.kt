package com.akggames.akg_sdk.ui.dialog.login

import android.app.Activity.RESULT_OK
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import com.akggames.akg_sdk.IConfig
import com.akggames.akg_sdk.dao.api.model.request.FacebookAuthRequest
import com.akggames.akg_sdk.presenter.AuthPresenter
import com.akggames.akg_sdk.rx.IView
//import com.akggames.akg_sdk.ui.BaseActivity
import com.akggames.akg_sdk.ui.dialog.BaseDialogFragment
import com.akggames.akg_sdk.ui.dialog.PhoneLoginDialogFragment
import com.akggames.android.R
import com.akggames.akg_sdk.util.DeviceUtil
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.FacebookSdk.getApplicationContext
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginResult
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.content_dialog_login.*
import kotlinx.android.synthetic.main.content_dialog_login.view.*


class LoginDialogFragment : BaseDialogFragment(), IView {
    override fun handleError(message: String) {

    }

    override fun handleRetryConnection() {

    }

    lateinit var mView: View
    lateinit var callbackManager: CallbackManager
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val presenter = AuthPresenter(this@LoginDialogFragment)


    var mDismissed: Boolean = true
    var mShownByMe = false
    var onViewDestroyed = true

    companion object {
        lateinit var myFragmentManager: FragmentManager

        fun newInstance(mFragmentManager: FragmentManager): LoginDialogFragment {
            val mDialogFragment = LoginDialogFragment()
            myFragmentManager = mFragmentManager
            return mDialogFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView =
            LayoutInflater.from(requireActivity() as Context).inflate(R.layout.content_dialog_login, container, true)
        return mView
    }

    override fun onStart() {
        super.onStart()
        mDismissed = false
        mShownByMe = true
        onViewDestroyed = false

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(requireActivity().application);

        initialize()
    }

    /*
     * Facebook SIGN IN----------------------------------------->
     */
    fun setFacebookLogin() {
        callbackManager = CallbackManager.Factory.create();
        mView.fbLoginButton.setFragment(this)
        mView.fbLoginButton.setPermissions(arrayListOf())

        mView.btnLoginFacebook.setOnClickListener {
            mView.fbLoginButton.performClick()
        }

        fbLoginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                var model = FacebookAuthRequest()
                model.access_token = result?.accessToken?.token
                model.auth_provider = "facebook"
                model.device_id = DeviceUtil().getImei(requireActivity())
                model.game_provider = "tes"
                model.operating_system = "android"
                model.phone_model = "samsung"
                presenter.facebookLogin(model, requireActivity())
            }

            override fun onCancel() {
                //Cancel
            }

            override fun onError(error: FacebookException?) {
                //App Code
            }
        })

    }


    /*
     * GOOGLE SIGN IN----------------------------------------->
     **/
    fun setGoogleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(IConfig.GOOGLE_CLIENT_ID)
            .build()

        val mGoogleAPIClient = GoogleApiClient.Builder(requireActivity())
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()
//

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        btnLoginGoogle.setOnClickListener {
            val signInIntent = mGoogleSignInClient.getSignInIntent()
            startActivityForResult(signInIntent, 101)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
            }
        }
    }

    fun initialize() {
        setGoogleLogin()
        setFacebookLogin()
        mView.btnLoginPhone.setOnClickListener {
            this.customDismiss()
//            showsDialog = true
            changeToPhoneLogin()
        }

        mView.btnGuest.setOnClickListener {
            this.dismiss()
        }

    }

    fun changeToPhoneLogin() {
        val phoneLoginDialogFragment =
            PhoneLoginDialogFragment.newInstance(myFragmentManager)
        val ftransaction = fragmentManager?.beginTransaction()
        ftransaction!!.addToBackStack("dialog")
        phoneLoginDialogFragment.show(ftransaction, "Phone")
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            var model = FacebookAuthRequest()
            model.access_token = account?.idToken
            model.auth_provider = "google"
            model.device_id = DeviceUtil().getImei(requireActivity())
            model.game_provider = "tes"
            model.operating_system = "android"
            model.phone_model = "samsung"
            model.expires_in = 3600
            AuthPresenter(this@LoginDialogFragment).googleLogin(model, requireActivity())

        } catch (e: ApiException) {
            Log.w("FRAGMENT_GOOGLE", "signInResult:failed code=" + e.statusCode)
        }
    }


}