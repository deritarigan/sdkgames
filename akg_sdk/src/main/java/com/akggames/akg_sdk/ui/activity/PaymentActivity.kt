package com.akggames.akg_sdk.ui.activity

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridLayout.VERTICAL
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akggames.akg_sdk.ui.adapter.PaymentAdapter
import com.akggames.android.sdk.R
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)


    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            rvProduct.layoutManager = GridLayoutManager(this,2,RecyclerView.VERTICAL,false)
        }else{
            rvProduct.layoutManager = GridLayoutManager(this,2,RecyclerView.HORIZONTAL,false)
        }
    }

    override fun onStart() {
        super.onStart()
        rvProduct.layoutManager = GridLayoutManager(this,2,RecyclerView.HORIZONTAL,false)

        rvProduct.adapter = PaymentAdapter(this)
        rvProduct.setHasFixedSize(true)
    }
}
