package com.akggames.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akggames.akg_sdk.rx.IView
import com.akggames.akg_sdk.ui.dialog.login.LoginDialogFragment
import com.facebook.CallbackManager

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), IView {
    override fun handleError(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handleRetryConnection() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    lateinit var callbackManager: CallbackManager
    lateinit var mGoogleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        try {
//            val info = packageManager.getPackageInfo(
//                "com.akggames.android",
//                PackageManager.GET_SIGNATURES
//            )
//            for (signature in info.signatures) {
//                val md = MessageDigest.getInstance("SHA")
//                md.update(signature.toByteArray())
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
//            }
//        } catch (e: PackageManager.NameNotFoundException) {
//
//        } catch (e: NoSuchAlgorithmException) {
//
//        }

        val f = supportFragmentManager
        val loginDialogFragment = LoginDialogFragment.newInstance(f)
        btnShow.setOnClickListener {
            val ftransaction = f.beginTransaction()

            loginDialogFragment.show(ftransaction, "Login")
        }

        init()
    }

    fun init() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this@MainActivity, gso)

        btnDismiss.setOnClickListener {
            val signInIntent = mGoogleSignInClient.getSignInIntent()
            startActivityForResult(signInIntent, 101)
        }
//        callbackManager = CallbackManager.Factory.create();
//
//        fbLoginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
//            override fun onSuccess(result: LoginResult?) {
//                val presenter = AuthPresenter(this@MainActivity)
//                var model = FacebookAuthRequest()
//                model.access_token = result?.accessToken?.token
//                model.auth_provider = "facebook"
//                model.device_id = DeviceUtil().getImei(this@MainActivity)
//                model.game_provider = "tes"
//                model.operating_system = "android"
//                model.phone_model = "samsung"
//                presenter.facebookLogin(model, this@MainActivity)
//            }
//
//            override fun onCancel() {
//                //Cancel
//            }
//
//            override fun onError(error: FacebookException?) {
//                //App Code
//            }
//        })
    }


}
