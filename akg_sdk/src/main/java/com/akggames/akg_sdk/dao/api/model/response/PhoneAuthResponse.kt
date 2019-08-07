package com.akggames.akg_sdk.dao.api.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class PhoneAuthResponse : BaseResponse() {

    var data: DataBean? = null
    var meta: MetaBean? = null

    class DataBean {
        var token:String? = null
        var message: String? = null
    }

    class MetaBean {
        var status: Boolean = false
        var code: Int = 0
    }
}