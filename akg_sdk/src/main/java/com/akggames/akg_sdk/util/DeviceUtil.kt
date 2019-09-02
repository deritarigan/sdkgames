package com.akggame.akg_sdk.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.telephony.TelephonyManager
import android.util.Log
import androidx.core.app.ActivityCompat
import java.util.*

class DeviceUtil {

    fun getImei(cuntext: Context): String {
        val manager = cuntext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (ActivityCompat.checkSelfPermission(
                cuntext,
                Manifest.permission.READ_PHONE_STATE
            ) === PackageManager.PERMISSION_GRANTED
        ) {
            return manager.deviceId
        } else {
            //            SessionManager.logout();
            Log.e("Get IMEI", "Tidak ada ijin untuk membuat id device")
            //Toast.makeText(cuntext, “Tidak ada ijin untuk membuat id device”, Toast.LENGTH_SHORT).show();
//            val intent = Intent(Intent.ACTION_MAIN)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//            intent.addCategory(Intent.CATEGORY_HOME)
            makeRequest(cuntext)

            return ""
        }
    }

    private fun makeRequest(context: Context) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.READ_PHONE_STATE),
            201
        )
    }

}