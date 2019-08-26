package com.akggames.android

import android.app.Application
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustConfig
import android.app.Activity
import android.os.Bundle
import com.adjust.sdk.LogLevel


class AkgApplication :Application() {

    override fun onCreate() {
        super.onCreate()
        val appToken = "y1t3z228xxj4"
        val environment = AdjustConfig.ENVIRONMENT_SANDBOX
        val config = AdjustConfig(this, appToken, environment)
        config.setLogLevel(LogLevel.VERBOSE); // enable all logs
        config.setLogLevel(LogLevel.DEBUG); // disable verbose logs
        config.setLogLevel(LogLevel.INFO); // disable debug logs (default)
        config.setLogLevel(LogLevel.WARN); // disable info logs
        config.setLogLevel(LogLevel.ERROR); // disable warning logs
        Adjust.onCreate(config)


        registerActivityLifecycleCallbacks(AdjustLifecycleCallbacks())
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