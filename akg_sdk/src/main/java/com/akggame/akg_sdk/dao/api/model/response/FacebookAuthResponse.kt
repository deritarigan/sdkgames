package com.akggame.akg_sdk.dao.api.model.response

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
class FacebookAuthResponse : BaseResponse() {

    var data: DataBean? = null
    var meta: MetaBean? = null

    @JsonIgnoreProperties(ignoreUnknown = true)
    class DataBean {
        var message: String? = null
        var token:String? = null
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    class MetaBean {
        var status: Boolean = false
        var code: Int = 0
    }
}