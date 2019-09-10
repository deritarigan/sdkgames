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
import android.util.Log
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustEvent
import com.akggame.akg_sdk.AKG_SDK
import com.akggame.akg_sdk.IConfig
import com.akggame.akg_sdk.dao.BillingDao
import com.akggame.akg_sdk.dao.api.model.request.PostOrderRequest
import com.akggame.akg_sdk.dao.api.model.response.BaseResponse
import com.akggame.akg_sdk.dao.api.model.response.GameProductsResponse
import com.akggame.akg_sdk.dao.pojo.PurchaseItem
import com.akggame.akg_sdk.presenter.OrderPresenter
import com.akggame.akg_sdk.presenter.ProductPresenter
import com.akggame.akg_sdk.util.CacheUtil
import com.android.billingclient.api.*
import com.google.android.gms.wallet.*


class PaymentActivity : AppCompatActivity(), PaymentIView,BillingDao.PaymentResponse {

    lateinit var mPaymentsClient: PaymentsClient
    val presenter = ProductPresenter(this)
    val orderPresenter = OrderPresenter(this)
    lateinit var adapter: PaymentAdapter
    lateinit private var billingDao: BillingDao
    lateinit var productData: GameProductsResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.akggame.android.sdk.R.layout.activity_payment)
        adapter = PaymentAdapter(this)
        onGetProduct()

    }

    override fun onStart() {
        super.onStart()
        rvProduct.layoutManager = GridLayoutManager(this, 2) as RecyclerView.LayoutManager?
        rvProduct.adapter = adapter
        rvProduct.setHasFixedSize(true)
    }

    override fun handleError(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    override fun handleRetryConnection() {

    }

    override fun doOnSuccess(data: GameProductsResponse) {
        productData = data
        billingDao = BillingDao(data.getListOfSKU(data.data),application,this, object : BillingDao.BillingDaoQuerySKU {
            override fun onQuerySKU(skuDetails: MutableList<SkuDetails>) {
                adapter.setInAppProduct(skuDetails,billingDao)
            }
        })

        billingDao.onInitiateBillingClient()
    }

    override fun doOnError(message: String) {

    }

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


    override fun onPaymentSuccess(purchase: Purchase) {

        setAdjustEventPaymentSuccess(billingDao.getPrice(productData.data,purchase.sku),purchase.sku)
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

    override fun onPaymentFailed() {
        setAdjustEventPaymentFailed()
    }

    fun setAdjustEventPaymentSuccess(price : Double,sku: String) {
        if(CacheUtil.getPreferenceString(IConfig.ADJUST_PAYMENT_SUCCESS,this)!=null){
            val adjustEvent =AdjustEvent(CacheUtil.getPreferenceString(IConfig.ADJUST_PAYMENT_SUCCESS,this))
            adjustEvent.setRevenue(price,"IDR")
            adjustEvent.setOrderId(sku)
            Adjust.trackEvent(adjustEvent)
        }
    }

    fun setAdjustEventPaymentFailed() {
        if(CacheUtil.getPreferenceString(IConfig.ADJUST_PAYMENT_FAILED,this)!=null){
            Adjust.trackEvent(AdjustEvent(CacheUtil.getPreferenceString(IConfig.ADJUST_PAYMENT_FAILED,this)))
        }
    }

    fun onGetProduct() {
        presenter.getProducts(CacheUtil.getPreferenceString(IConfig.SESSION_GAME,this), this)
    }

}
