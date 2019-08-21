package com.akggames.akg_sdk.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.akggames.akg_sdk.dao.PaymentDao
import com.akggames.akg_sdk.dao.api.model.response.GameProductsResponse
import com.akggames.android.sdk.R
import com.google.android.gms.wallet.AutoResolveHelper
import com.google.android.gms.wallet.PaymentDataRequest
import com.google.android.gms.wallet.PaymentsClient
import kotlinx.android.synthetic.main.content_dialog_verify.*
import kotlinx.android.synthetic.main.item_list_product.view.*

class PaymentAdapter(val context: Context,var mPaymentsClient : PaymentsClient) : RecyclerView.Adapter<PaymentAdapter.ViewHolder>() {
    var listData = mutableListOf<GameProductsResponse.DataBean>()
    lateinit var view :View
    var isReadyToPayment = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentAdapter.ViewHolder {
        view = LayoutInflater.from(context).inflate(R.layout.item_list_product,null)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: PaymentAdapter.ViewHolder, position: Int) {
        var data = listData.get(position)
        holder.tvProductName.text = data.attributes?.name
        holder.tvProductName.setOnClickListener {
            if(isReadyToPayment){
                requestPayment(it)
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvProductName = itemView.tvProductName

    }

    fun setData(data:MutableList<GameProductsResponse.DataBean>){
        listData = data
        notifyDataSetChanged()
    }

    fun setPayment(isReady:Boolean){
        isReadyToPayment = isReady
    }

    fun requestPayment(view: View) {
        val paymentDataRequestJson = PaymentDao().getPaymentDataRequest()
        val request = PaymentDataRequest.fromJson(paymentDataRequestJson.toString())
        if (request != null) {
            AutoResolveHelper.resolveTask(
                mPaymentsClient.loadPaymentData(request), context as AppCompatActivity, 133
            )
        }
    }
}