package com.akggames.android

import android.app.Application
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustConfig
import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.adjust.sdk.LogLevel
import com.akggames.akg_sdk.AKG_SDK


class AkgApplication :Application() {

    override fun onCreate() {
        super.onCreate()
//        AKG_SDK().registerAdjustOnAKG(this)
        val appToken = "y1t3z228xxj4"
        val environment = AdjustConfig.ENVIRONMENT_PRODUCTION
        val config = AdjustConfig(this, appToken, environment)
        config.setLogLevel(LogLevel.VERBOSE); // enable all logs
        Adjust.onCreate(config)
        Log.d("Adjust", "Initiate")
    }

    private class AdjustLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
        override fun onActivityStarted(p0: Activity) {

        }

        override fun onActivityDestroyed(p0: Activity) {

        }

        override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {

        }

        override fun onActivityStopped(p0: Activity) {

        }

        override fun onActivityCreated(p0: Activity, p1: Bundle?) {

        }

        override fun onActivityResumed(activity: Activity) {
            Adjust.onResume()
        }

        override fun onActivityPaused(activity: Activity) {
            Adjust.onPause()
        }
    }
}