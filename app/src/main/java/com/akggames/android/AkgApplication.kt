package com.akggames.android

import android.app.Application
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustConfig
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.adjust.sdk.LogLevel
import com.akggames.akg_sdk.AKG_SDK
import com.adjust.sdk.OnDeviceIdsRead


class AkgApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AKG_SDK().registerAdjustOnAKG(this)
    }

}