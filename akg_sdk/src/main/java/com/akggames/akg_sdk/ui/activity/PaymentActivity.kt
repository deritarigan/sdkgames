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
import androidx.recyclerview.widget.LinearLayoutManager
import com.akggames.akg_sdk.dao.BillingDao
import com.akggames.akg_sdk.dao.api.model.response.GameProductsResponse
import com.akggames.akg_sdk.presenter.ProductPresenter
import com.akggames.akg_sdk.rx.IView
import com.android.billingclient.api.*
import com.android.billingclient.api.BillingClient.BillingResponseCode.OK
import com.google.android.gms.wallet.*
import io.reactivex.plugins.RxJavaPlugins.onError
import org.json.JSONObject


class PaymentActivity : AppCompatActivity(), PaymentIView {


    lateinit var mPaymentsClient: PaymentsClient
    val presenter = ProductPresenter(this)
    lateinit var adapter: PaymentAdapter
    lateinit private var billingDao: BillingDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.akggames.android.sdk.R.layout.activity_payment)
        billingDao = BillingDao(application, object : BillingDao.BillingDaoQuerySKU {
            override fun onQuerySKU(skuDetails: SkuDetails) {
                if (skuDetails.sku == BillingDao.SKU.janjiDoang) {
                    tvProd1.text = skuDetails.title + " -->" + skuDetails.price
                }else{
                    tvProd1.text = skuDetails.title + " -->" + skuDetails.price
                }
                if (skuDetails.sku == BillingDao.SKU.tempeOrek) {
                    tvProd2.text = skuDetails.title + " -->" + skuDetails.price
                }else{
                    tvProd2.text = skuDetails.title + " -->" + skuDetails.price
                }
            }

        })
        adapter = PaymentAdapter(this,billingDao)


        billingDao.onInitiateBillingClient()
    }


    fun onGetProduct() {
        presenter.getProducts("mobile-legends", this)
    }

    override fun onStart() {
        super.onStart()
        rvProduct.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false)
        rvProduct.adapter = adapter
        rvProduct.setHasFixedSize(true)
        onGetProduct()
    }

    override fun handleError(message: String) {

    }

    override fun handleRetryConnection() {

    }

    override fun doOnSuccess(data: GameProductsResponse) {
        adapter.setData(data.data as MutableList<GameProductsResponse.DataBean>)
    }

    override fun doOnError(message: String) {

    }
}