package com.akggame.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.akggame.akg_sdk.AKG_SDK
import com.akggame.akg_sdk.MenuSDKCallback
import com.akggame.akg_sdk.ui.activity.PaymentActivity
import com.akggame.akg_sdk.ui.dialog.login.RelaunchDialog
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity(),MenuSDKCallback {
    override fun onSuccessBind(token: String) {

    }

    override fun onLogout() {
        startActivity(Intent(this@Main2Activity,MainActivity::class.java))
        finish()
    }


    val sdkgames = AKG_SDK(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        sdkgames.setFloatingButton(floatingButton, this,this)
        floatingButton.float()

        btnPayment.setOnClickListener {
            sdkgames.onSDKPayment()
//sdkgames.setRelauchDialog(this)
        }
    }
}
