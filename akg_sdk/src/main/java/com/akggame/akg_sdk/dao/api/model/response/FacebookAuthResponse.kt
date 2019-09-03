package com.akggame.akg_sdk.dao.api.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class FacebookAuthResponse : BaseResponse() {

    var data: DataBean? = null
    var meta: MetaBean? = null

    class DataBean {
        var message: String? = null
        var token:String? = null
    }

    class MetaBean {
        var status: Boolean = false
        var code: Int = 0
    }
}