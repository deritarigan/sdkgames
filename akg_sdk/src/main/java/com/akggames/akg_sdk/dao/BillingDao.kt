package com.akggame.akg_sdk.dao

import android.app.Activity
import android.app.Application
import android.util.Log
import com.android.billingclient.api.*

class BillingDao constructor(private val application: Application, val queryCallback: BillingDaoQuerySKU) :
    PurchasesUpdatedListener, BillingClientStateListener {

    val LOG_TAG = "Billing Dao :"
    lateinit var billingClient: BillingClient

    object SKU {
        val janjiDoang = "com.sdkgame.product1"
        val tempeOrek = "com.sdkgame.product2"

        val myListSKU = listOf(janjiDoang, tempeOrek)
    }

    interface BillingDaoQuerySKU {
        fun onQuerySKU(skuDetails: MutableList<SkuDetails>)
    }

    fun onInitiateBillingClient() {
        billingClient = BillingClient.newBuilder(application.applicationContext)
            .setListener(this@BillingDao)
            .enablePendingPurchases()
            .build()
        connectToPlayBillingService()
    }

    private fun connectToPlayBillingService(): Boolean {
        Log.d(LOG_TAG, "connectToPlayBillingService")
        if (!billingClient.isReady) {
            billingClient.startConnection(this)
            return true
        }
        return false
    }

    fun querySkuDetailsAsync(@BillingClient.SkuType skuType: String, skuList: List<String>) {

        val params = SkuDetailsParams.newBuilder()
            .setSkusList(skuList)
            .setType(skuType)
            .build()

        billingClient.querySkuDetailsAsync(params, object : SkuDetailsResponseListener {
            override fun onSkuDetailsResponse(billingResult: BillingResult?, skuDetailsList: MutableList<SkuDetails>?) {
                if (billingResult?.responseCode == BillingClient.BillingResponseCode.OK) {
                    Log.d(LOG_TAG, "onBillingResultResponseCode is OK")
                    if (skuDetailsList != null) {
                        queryCallback.onQuerySKU(skuDetailsList)
                    }
                } else {
                    Log.e(LOG_TAG, billingResult?.debugMessage)
                    Log.e(LOG_TAG, billingResult?.debugMessage.toString())
                }
            }

        })
    }

    fun lauchBillingFlow(activity: Activity, skuDetails: SkuDetails){
        var billingFlowParams = BillingFlowParams.newBuilder()
            .setSkuDetails(skuDetails)
            .build()
        billingClient.launchBillingFlow(activity,billingFlowParams)
    }

    override fun onBillingServiceDisconnected() {
        Log.d(LOG_TAG, "connectToPlayBillingService")
        connectToPlayBillingService()
    }

    override fun onBillingSetupFinished(billingResult: BillingResult?) {
        when (billingResult?.responseCode) {
            BillingClient.BillingResponseCode.OK -> {
                Log.d(LOG_TAG, "onBillingSetupFinished successfully")
                querySkuDetailsAsync(BillingClient.SkuType.INAPP, SKU.myListSKU)
//                queryPurchasesAsync()
            }
            BillingClient.BillingResponseCode.BILLING_UNAVAILABLE -> {
                //Some apps may choose to make decisions based on this knowledge.
                Log.d(LOG_TAG, billingResult.debugMessage)
            }
            else -> {
                //do nothing. Someone else will connect it through retry policy.
                //May choose to send to server though
                Log.d(LOG_TAG, billingResult?.debugMessage)
            }
        }
    }

    override fun onPurchasesUpdated(billingResult: BillingResult?, purchases: MutableList<Purchase>?) {

    }

}