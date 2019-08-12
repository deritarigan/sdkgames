package com.akggames.akg_sdk.ui.component

import com.akggames.akg_sdk.dao.api.model.FloatingItem

internal data class ItemWrapper(
    val floatingItem: FloatingItem,
    var itemSize: Int,
    val parentWidth: Int,
    var orientation: FloatingButton.ORIENTATION
)