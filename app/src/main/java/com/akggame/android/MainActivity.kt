package com.akggame.android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustConfig
import com.adjust.sdk.AdjustEvent
import com.adjust.sdk.LogLevel
import com.akggame.akg_sdk.AKG_SDK
import com.akggame.akg_sdk.LoginSDKCallback
import com.crashlytics.android.Crashlytics

import io.fabric.sdk.android.Fabric


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

        sdkgames.onLogin("mobile-legends",object : LoginSDKCallback{
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
