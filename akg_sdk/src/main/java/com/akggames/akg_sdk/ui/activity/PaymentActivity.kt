package com.akggames.akg_sdk.ui.activity

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridLayout.VERTICAL
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akggames.akg_sdk.ui.adapter.PaymentAdapter
import kotlinx.android.synthetic.main.activity_payment.*
import com.google.android.gms.common.api.ApiException
import com.akggames.akg_sdk.dao.PaymentDao
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import io.reactivex.annotations.NonNull
import android.R
import android.app.Activity
import android.content.Intent
import android.location.Geocoder.isPresent
import android.view.View
import com.google.android.gms.wallet.*
import io.reactivex.plugins.RxJavaPlugins.onError
import org.json.JSONObject


class PaymentActivity : AppCompatActivity() {
    lateinit var mPaymentsClient: PaymentsClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.akggames.android.sdk.R.layout.activity_payment)
        mPaymentsClient = Wallet.getPaymentsClient(
            this,
            Wallet.WalletOptions.Builder()
                .setEnvironment(WalletConstants.ENVIRONMENT_TEST)
                .build()
        )

        val request = IsReadyToPayRequest.fromJson(PaymentDao().getIsReadyToPayRequest().toString())
        val task = mPaymentsClient.isReadyToPay(request)
        task.addOnCompleteListener(
            object : OnCompleteListener<Boolean> {
                override fun onComplete(@NonNull task: Task<Boolean>) {
                    try {
                        val result = task.getResult(ApiException::class.java)
                        if (result!!) {
                            Toast.makeText(this@PaymentActivity, "onCompletePayment", Toast.LENGTH_LONG).show()

                            googlePlayButton.setOnClickListener {
                                requestPayment(it)
                            }

                        }
                    } catch (e: ApiException) {
                    }

                }
            })

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            rvProduct.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        } else {
            rvProduct.layoutManager = GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false)
        }
    }

    override fun onStart() {
        super.onStart()
        rvProduct.layoutManager = GridLayoutManager(this, 2)

        rvProduct.adapter = PaymentAdapter(this)
        rvProduct.setHasFixedSize(true)
    }

    fun requestPayment(view: View) {
        val paymentDataRequestJson = PaymentDao().getPaymentDataRequest()
//        if (!paymentDataRequestJson.) {
//            return
//        }
        val request = PaymentDataRequest.fromJson(paymentDataRequestJson.toString())
        if (request != null) {
            AutoResolveHelper.resolveTask(
                mPaymentsClient.loadPaymentData(request), this, 133
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            133 -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        data?.let {
                            //                            onPaymentSuccess(PaymentData.getFromIntent(data))
                        }
                    }
                    Activity.RESULT_CANCELED -> {
                    }
                    AutoResolveHelper.RESULT_ERROR -> {
                    }
                }
                googlePlayButton.isClickable = true
            }
        }
    }
}
