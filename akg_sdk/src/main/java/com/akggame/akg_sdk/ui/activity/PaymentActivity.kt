package com.akggame.akg_sdk.ui.activity

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridLayout.VERTICAL
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akggame.akg_sdk.ui.adapter.PaymentAdapter
import kotlinx.android.synthetic.main.activity_payment.*
import com.google.android.gms.common.api.ApiException
import com.akggame.akg_sdk.dao.PaymentDao
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import io.reactivex.annotations.NonNull
import android.R
import android.app.Activity
import android.content.Intent
import android.location.Geocoder.isPresent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.akggame.akg_sdk.IConfig
import com.akggame.akg_sdk.dao.BillingDao
import com.akggame.akg_sdk.dao.api.model.response.GameProductsResponse
import com.akggame.akg_sdk.presenter.ProductPresenter
import com.akggame.akg_sdk.rx.IView
import com.akggame.akg_sdk.util.CacheUtil
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
        setContentView(com.akggame.android.sdk.R.layout.activity_payment)
        billingDao = BillingDao(application, object : BillingDao.BillingDaoQuerySKU {
            override fun onQuerySKU(skuDetails: MutableList<SkuDetails>) {
                adapter.setInAppProduct(skuDetails)
            }

        })
        adapter = PaymentAdapter(this,billingDao)


        billingDao.onInitiateBillingClient()
    }


    fun onGetProduct() {
        presenter.getProducts(CacheUtil.getPreferenceString(IConfig.SESSION_GAME,this), this)
    }

    override fun onStart() {
        super.onStart()
        rvProduct.layoutManager = GridLayoutManager(this, 2)
        rvProduct.adapter = adapter
        rvProduct.setHasFixedSize(true)
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
