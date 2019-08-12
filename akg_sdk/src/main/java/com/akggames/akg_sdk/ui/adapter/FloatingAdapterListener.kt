package com.akggames.akg_sdk.ui.adapter

import com.akggames.akg_sdk.dao.api.model.FloatingItem


interface FloatingAdapterListener {
    fun onItemClick(position: Int, floatingItem: FloatingItem)

}