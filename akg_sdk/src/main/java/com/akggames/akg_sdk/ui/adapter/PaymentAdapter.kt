package com.akggames.akg_sdk.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akggames.android.sdk.R
import kotlinx.android.synthetic.main.content_dialog_verify.*

class PaymentAdapter(val context: Context) : RecyclerView.Adapter<PaymentAdapter.ViewHolder>() {
    lateinit var listData : MutableList<Any>
    lateinit var view :View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentAdapter.ViewHolder {
        view = LayoutInflater.from(context).inflate(R.layout.item_list_product,null)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 9
    }

    override fun onBindViewHolder(holder: PaymentAdapter.ViewHolder, position: Int) {

    }

    class ViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}