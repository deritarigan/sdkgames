package com.akggame.android

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.akggame.akg_sdk.AKG_SDK
import com.akggame.akg_sdk.MenuSDKCallback
import com.akggame.akg_sdk.dao.pojo.PurchaseItem
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


//    val sdkgames = AKG_SDK(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        AKG_SDK.setFloatingButton(this,floatingButton, this,this)
        floatingButton.float()

        btnPayment.setOnClickListener {
            AKG_SDK.onSDKPayment(this)
//sdkgames.setRelauchDialog(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== Activity.RESULT_OK){
            if(requestCode == AKG_SDK.SDK_PAYMENT_CODE){
                val payment : PurchaseItem? = data?.getParcelableExtra("orderDetail")

                Toast.makeText(this,payment?.product_name,Toast.LENGTH_LONG).show()
            }
        }
    }
}
