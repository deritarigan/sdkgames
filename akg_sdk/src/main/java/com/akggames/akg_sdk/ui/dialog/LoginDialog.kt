package com.akggames.android.ui.dialog

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.core.app.ActivityCompat.startActivityForResult
import app.beelabs.com.codebase.base.BaseDialog
import com.akggames.android.R
import com.facebook.*
import com.facebook.FacebookSdk.getApplicationContext
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.android.synthetic.main.content_dialog_login.*
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class LoginDialog(val activity: Activity, val style: Int, val AppContext: Application) : BaseDialog(activity, style) {

    lateinit var callbackManager: CallbackManager
    lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val configuration = activity.resources.configuration
//        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
            setWindowContentDialogLayout(R.layout.content_dialog_login)
//        else if (configuration.orientation==Configuration.ORIENTATION_PORTRAIT)
//            setWindowContentDialogLayout(R.layout.content_dialog_login_phone)

//        if(activity.isChangingConfigurations){
//            if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
//                setWindowContentDialogLayout(R.layout.content_dialog_login)
//            else if (configuration.orientation==Configuration.ORIENTATION_PORTRAIT)
//                setWindowContentDialogLayout(R.layout.content_dialog_login_phone)
//        }

//
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
//            .requestEmail()
//            .build()
//        mGoogleSignInClient = GoogleSignIn.getClient(context, gso)
//        // Build a GoogleSignInClient with the options specified by gso.
//
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        AppEventsLogger.activateApp(AppContext);
//        val w = window
//        w?.setBackgroundDrawable(ColorDrawable(context.resources.getColor(R.color.transparent)))
//        initialize()

    }

    private fun initialize() {
//        val accessToken:AccessToken = AccessToken.getCurrentAccessToken()

        callbackManager = CallbackManager.Factory.create();
        btnLoginFacebook.setOnClickListener {
            fbLoginButton.performClick()
        }

        btnLoginGoogle.setOnClickListener {
            val signInIntent = mGoogleSignInClient.getSignInIntent()
            startActivityForResult(activity, signInIntent, 101, Bundle())
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
    }



}
