package com.akggame.akg_sdk.dao.api.model.response

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class PhoneAuthResponse : BaseResponse() {

    var data: DataBean? = null
    var meta: MetaBean? = null

    @JsonIgnoreProperties(ignoreUnknown = true)
    class DataBean {
        var token:String? = null
        var message: String? = null
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    class MetaBean {
        var status: Boolean = false
        var code: Int = 0
    }
}