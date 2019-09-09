package com.akggame.android

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import com.akggame.akg_sdk.AKG_SDK
import com.akggame.akg_sdk.MenuSDKCallback
import com.akggame.akg_sdk.dao.pojo.PurchaseItem
import com.akggame.akg_sdk.ui.activity.PaymentActivity
import com.akggame.akg_sdk.ui.dialog.login.RelaunchDialog
import kotlinx.android.synthetic.main.activity_main2.*
import android.content.Context.WINDOW_SERVICE
import android.content.res.Configuration
import android.view.WindowManager



class Main2Activity : AppCompatActivity(),MenuSDKCallback {
    override fun onSuccessBind(token: String) {

    }

    override fun onLogout() {
        startActivity(Intent(this@Main2Activity,MainActivity::class.java))
        finish()
    }


//    val sdkgames = AKG_SDK(this)
    val displayMetrics = DisplayMetrics();

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

    override fun onStart() {
        super.onStart()

        val windowmanager = applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        windowmanager.defaultDisplay.getMetrics(displayMetrics)

        val height = Math.round(displayMetrics.heightPixels / displayMetrics.density);

        val width = Math.round(displayMetrics.widthPixels / displayMetrics.density);
        val screenSizeMask = resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK
        var screenSize = ""
        when(screenSizeMask){
            Configuration.SCREENLAYOUT_SIZE_LARGE-> screenSize = "large"
            Configuration.SCREENLAYOUT_SIZE_NORMAL-> screenSize = "normal"
            Configuration.SCREENLAYOUT_SIZE_SMALL-> screenSize = "small"
            Configuration.SCREENLAYOUT_SIZE_XLARGE-> screenSize = "xlarge"
        }


        Log.d("SDK Debug ","height " +height)
        Log.d("SDK Debug ","width " +width)
        Log.d("SDK Debug ","dm hp " +displayMetrics.heightPixels)
        Log.d("SDK Debug ","dm wp " +displayMetrics.widthPixels)
        Log.d("SDK Debug ","dm density " +displayMetrics.density)
        Log.d("SDK Debug ","screen size mask "+ screenSize)
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
