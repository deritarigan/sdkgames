package com.akggame.akg_sdk.ui.dialog.login

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustEvent
import com.akggame.akg_sdk.IConfig
import com.akggame.akg_sdk.LoginSDKCallback
import com.akggame.akg_sdk.dao.api.model.request.FacebookAuthRequest
import com.akggame.akg_sdk.dao.api.model.request.GuestLoginRequest
import com.akggame.akg_sdk.presenter.LoginPresenter
//import com.akggame.akg_sdk.ui.BaseActivity
import com.akggame.akg_sdk.ui.dialog.BaseDialogFragment
import com.akggame.akg_sdk.ui.dialog.PhoneLoginDialogFragment
import com.akggame.akg_sdk.util.CacheUtil
import com.akggame.android.sdk.R
import com.akggame.akg_sdk.util.DeviceUtil
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.content_dialog_login.*
import kotlinx.android.synthetic.main.content_dialog_login.view.*


class LoginDialogFragment() : BaseDialogFragment(), LoginIView {

    lateinit var mView: View
    lateinit var callbackManager: CallbackManager
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val presenter = LoginPresenter(this@LoginDialogFragment)


    var mDismissed: Boolean = true
    var mShownByMe = false
    var onViewDestroyed = true

    constructor(fm: FragmentManager?) : this() {
        myFragmentManager = fm
    }

    companion object {
        private lateinit var mLoginCallback: LoginSDKCallback

        fun newInstance(mFragmentManager: FragmentManager, loginCallback: LoginSDKCallback): LoginDialogFragment {
            val mDialogFragment = LoginDialogFragment(mFragmentManager)
            mLoginCallback = loginCallback
            return mDialogFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView =
            LayoutInflater.from(requireActivity() as Context).inflate(R.layout.content_dialog_login, null, false)

        return mView
    }

    override fun onStart() {
        super.onStart()
        mDismissed = false
        mShownByMe = true
        onViewDestroyed = false

        AppEventsLogger.activateApp(requireActivity().application);

        initialize()
    }

    override fun doOnSuccess(token: String,loginType:String) {
        mLoginCallback.onResponseSuccess(token,loginType)
        setAdjustEventLogin()
        dismiss()
    }

    fun setAdjustEventLogin(){
        if(CacheUtil.getPreferenceString(IConfig.ADJUST_LOGIN,requireActivity())!=null){
            Adjust.trackEvent(AdjustEvent(CacheUtil.getPreferenceString(IConfig.ADJUST_LOGIN,requireActivity())))
        }
    }

    override fun doOnError(message: String) {
        mLoginCallback.onResponseFailed(message)
        customDismiss()
        clearBackStack()
    }

    /*
     * Facebook SIGN IN----------------------------------------->
     */
    fun setFacebookLogin() {
        callbackManager = CallbackManager.Factory.create();
        mView.fbLoginButton.setFragment(this)
        mView.fbLoginButton.setPermissions(arrayListOf("email"))

        mView.btnBindFacebook.setOnClickListener {
            if (DeviceUtil.getImei(requireActivity()).isNotEmpty()) {
                mView.fbLoginButton.performClick()
            }
        }

        fbLoginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                var model = FacebookAuthRequest()
                model.access_token = result?.accessToken?.token
                model.auth_provider = "facebook"
                model.device_id = DeviceUtil.getImei(requireActivity())
                model.game_provider = CacheUtil.getPreferenceString(IConfig.SESSION_GAME,requireActivity())
                model.operating_system = "android"
                model.phone_model = DeviceUtil.getDeviceName()
                presenter.facebookLogin(model, requireActivity())
            }

            override fun onCancel() {
            }

            override fun onError(error: FacebookException?) {
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

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        btnBindGoogle.setOnClickListener {
            if (DeviceUtil.getImei(requireActivity()).isNotEmpty()) {
                val signInIntent = mGoogleSignInClient.getSignInIntent()
                startActivityForResult(signInIntent, 101)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
//            if (resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
//            }
        }
    }

    fun initialize() {
        setGoogleLogin()
        setFacebookLogin()
        mView.btnBack.setOnClickListener {
            if (DeviceUtil.getImei(requireActivity()).isNotEmpty()) {
                this.customDismiss()
                changeToPhoneLogin()
            }
        }

        mView.btnGuest.setOnClickListener {
            if (DeviceUtil.getImei(requireActivity()).isNotEmpty()) {
                val model = GuestLoginRequest(
                    "guest",
                    DeviceUtil.getImei(requireActivity()),
                    CacheUtil.getPreferenceString(IConfig.SESSION_GAME,requireActivity()),
                    "Android",
                    DeviceUtil.getDeviceName()
                )
                presenter.guestLogin(model, requireActivity())
            }
        }
    }

    fun changeToPhoneLogin() {
        if(myFragmentManager!=null){
            val phoneLoginDialogFragment =
                PhoneLoginDialogFragment.newInstance(myFragmentManager, mLoginCallback)
            val ftransaction = myFragmentManager!!.beginTransaction()
            ftransaction.addToBackStack("phone")
            phoneLoginDialogFragment.show(ftransaction, "phone")
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            var model = FacebookAuthRequest()
            model.access_token = account?.idToken
            model.auth_provider = "google"
            model.device_id = DeviceUtil.getImei(requireActivity())
            model.game_provider = CacheUtil.getPreferenceString(IConfig.SESSION_GAME,requireActivity())
            model.operating_system = "android"
            model.phone_model = DeviceUtil.getDeviceName()
            model.expires_in = 3600
            LoginPresenter(this@LoginDialogFragment).googleLogin(model, requireActivity())
        } catch (e: ApiException) {
            Log.w("FRAGMENT_GOOGLE", "signInResult:failed code=" + e.statusCode)
        }
    }

}