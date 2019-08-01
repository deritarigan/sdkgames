package com.akggames.akg_sdk.dao.api.model.response

open class BaseReponse {

    var data: DataBean? = null
    var meta: MetaBean? = null

    class DataBean

    class MetaBean {

        var isStatus: Boolean = false
        var code: Int = 0
        var message: String? = null
    }
}
