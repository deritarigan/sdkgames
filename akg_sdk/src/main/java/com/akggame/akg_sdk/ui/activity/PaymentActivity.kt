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
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.akggame.akg_sdk.AKG_SDK
import com.akggame.akg_sdk.IConfig
import com.akggame.akg_sdk.dao.BillingDao
import com.akggame.akg_sdk.dao.api.model.request.PostOrderRequest
import com.akggame.akg_sdk.dao.api.model.response.BaseResponse
import com.akggame.akg_sdk.dao.api.model.response.GameProductsResponse
import com.akggame.akg_sdk.dao.pojo.PurchaseItem
import com.akggame.akg_sdk.presenter.OrderPresenter
import com.akggame.akg_sdk.presenter.ProductPresenter
import com.akggame.akg_sdk.rx.IView
import com.akggame.akg_sdk.util.CacheUtil
import com.android.billingclient.api.*
import com.android.billingclient.api.BillingClient.BillingResponseCode.OK
import com.google.android.gms.wallet.*
import io.reactivex.plugins.RxJavaPlugins.onError
import org.json.JSONObject


class PaymentActivity : AppCompatActivity(), PaymentIView,BillingDao.PaymentResponse {
    override fun doOnSuccessPost(o: BaseResponse, purchase: Purchase) {
       Toast.makeText(this,"Success post data",Toast.LENGTH_LONG).show()
    }

    override fun doOnComplete(purchase: Purchase) {
        val purchaseItem = PurchaseItem()
        purchaseItem.product_id= purchase.sku
        purchaseItem.product_name = purchase.packageName

        intent = Intent()
        intent.putExtra(AKG_SDK.SDK_PAYMENT_DATA,purchaseItem)
        setResult(Activity.RESULT_OK,intent)
        finish()
    }



    lateinit var mPaymentsClient: PaymentsClient
    val presenter = ProductPresenter(this)
    val orderPresenter = OrderPresenter(this)
    lateinit var adapter: PaymentAdapter
    lateinit private var billingDao: BillingDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.akggame.android.sdk.R.layout.activity_payment)
        billingDao = BillingDao(application,this, object : BillingDao.BillingDaoQuerySKU {
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
        rvProduct.layoutManager = GridLayoutManager(this, 2) as RecyclerView.LayoutManager?
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

    override fun onPaymentSuccess(purchase: Purchase) {

        val postOrderRequest = PostOrderRequest("Google Play",
            purchase.purchaseTime,
            CacheUtil.getPreferenceString(IConfig.SESSION_GAME,this)!!,
            purchase.orderId,
            purchase.packageName,
            3000,
            "Android",
            "com.sdkgame.product1",
            purchase.purchaseToken,
            purchase.sku,
            "Success",
            CacheUtil.getPreferenceString(IConfig.SESSION_UID,this),
            CacheUtil.getPreferenceString(IConfig.SESSION_USERNAME,this)
            )

        orderPresenter.onPostOrder(postOrderRequest,purchase,this)
//        purchaseItem.amount = purchases.get(0).or

    }


}
