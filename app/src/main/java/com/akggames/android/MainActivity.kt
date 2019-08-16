package com.akggames.android

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.akggames.akg_sdk.AKG_SDK
import com.akggames.akg_sdk.AkgApp
import com.akggames.akg_sdk.IConfig
import com.akggames.akg_sdk.LoginSDKCallback
import com.akggames.akg_sdk.dao.api.model.FloatingItem
import com.akggames.akg_sdk.ui.adapter.FloatingAdapterListener
import com.akggames.akg_sdk.ui.dialog.BaseDialogFragment
import com.akggames.akg_sdk.ui.dialog.login.LoginDialogFragment
import com.akggames.akg_sdk.ui.dialog.menu.*
import com.akggames.akg_sdk.util.CacheUtil
import com.crashlytics.android.Crashlytics
import com.facebook.CallbackManager

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity() {

    val sdkgames:AKG_SDK = AKG_SDK(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())
        setContentView(R.layout.activity_main)
        if (sdkgames.checkIsLogin(this)){
            startActivity(Intent(this@MainActivity,Main2Activity::class.java))
            finish()
        }
        sdkgames.onLogin(object : LoginSDKCallback{
            override fun onResponseSuccess(token: String) {
                Toast.makeText(this@MainActivity, token, Toast.LENGTH_LONG).show()
                startActivity(Intent(this@MainActivity,Main2Activity::class.java))
                finish()
            }
            override fun onResponseFailed(message: String) {
                Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
            }
        })
    }


}
