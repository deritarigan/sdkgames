package com.akggame.akg_sdk.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.akggame.akg_sdk.dao.BillingDao
import com.akggame.akg_sdk.dao.PaymentDao
import com.akggame.akg_sdk.dao.api.model.response.GameProductsResponse
import com.akggame.android.sdk.R
import com.android.billingclient.api.SkuDetails
import com.google.android.gms.wallet.AutoResolveHelper
import com.google.android.gms.wallet.PaymentDataRequest
import com.google.android.gms.wallet.PaymentsClient
import kotlinx.android.synthetic.main.content_dialog_verify.*
import kotlinx.android.synthetic.main.item_list_product.view.*

class PaymentAdapter(val context: Context,val billingDao: BillingDao) : RecyclerView.Adapter<PaymentAdapter.ViewHolder>() {
    var listData = mutableListOf<GameProductsResponse.DataBean>()
    lateinit var view: View
    var isReadyToPayment = false
    var skuDetails = mutableListOf<SkuDetails>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentAdapter.ViewHolder {
        view = LayoutInflater.from(context).inflate(R.layout.item_list_product, null)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return skuDetails.size
    }

    override fun onBindViewHolder(holder: PaymentAdapter.ViewHolder, position: Int) {
        var data = skuDetails.get(position)
        holder.tvProductName.text = data.title +" : "+ data.price
        holder.tvProductName.setOnClickListener {
            billingDao.lauchBillingFlow(context as AppCompatActivity,data)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvProductName = itemView.tvProductName

    }

    fun setData(data: MutableList<GameProductsResponse.DataBean>) {
        listData = data
        notifyDataSetChanged()
    }

    fun setInAppProduct(skuList: MutableList<SkuDetails>) {
        skuDetails = skuList
        notifyDataSetChanged()
    }

    fun setPayment(isReady: Boolean) {
        isReadyToPayment = isReady
    }

    fun requestPayment(view: View) {
    }
}