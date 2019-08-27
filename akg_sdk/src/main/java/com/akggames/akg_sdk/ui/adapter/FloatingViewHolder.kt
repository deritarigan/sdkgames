package com.akggames.akg_sdk.ui.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.View
import android.widget.RelativeLayout
import androidx.core.widget.ImageViewCompat
import com.akggames.akg_sdk.dao.api.model.FloatingItem
import com.akggames.akg_sdk.extension.dp2Px
import com.akggames.akg_sdk.ui.component.FloatingButton
import com.akggames.akg_sdk.ui.component.ItemWrapper
import com.akggames.android.sdk.R
import kotlinx.android.synthetic.main.item_floating_button.view.*

class FloatingViewHolder(
    private val onItemClickListener: FloatingAdapterListener? = null,
    view: View
) : BaseViewHolder(view) {

    private lateinit var wrapper: ItemWrapper
    private lateinit var floatingItem: FloatingItem

    override fun bindData(data: Any) {
        if (data is ItemWrapper) {
            this.wrapper = data
            this.floatingItem = data.floatingItem
            drawItemUI()
        }
    }

    private fun drawItemUI() {
        itemView.apply {
            if (wrapper.orientation == FloatingButton.ORIENTATION.HORIZONTAL) {
                val params = RelativeLayout.LayoutParams(
                    wrapper.parentWidth / wrapper.itemSize, RelativeLayout.LayoutParams.MATCH_PARENT)
//                val params = RelativeLayout.LayoutParams(
//                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT)
                this.layoutParams = params

            } else {
                val params = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, wrapper.parentWidth / wrapper.itemSize)
                this.layoutParams = params
            }


            item_floating_icon.setImageDrawable(floatingItem.icon)

            floatingItem.iconForm?.let {
//                item_floating_icon.layoutParams.width = context.dp2Px(it.iconSize)
//                item_floating_icon.layoutParams.height = context.dp2Px(it.iconSize)
                item_floating_icon.layoutParams.width = 84
                item_floating_icon.layoutParams.height = 84
                item_floating_icon.scaleType = it.iconScaleType
                ImageViewCompat.setImageTintList(item_floating_icon, ColorStateList.valueOf(it.iconColor))
            }
        }
    }

    override fun onClick(p0: View?) {
        this.onItemClickListener?.onItemClick(adapterPosition, floatingItem)
    }

    override fun onLongClick(p0: View?) = false
}