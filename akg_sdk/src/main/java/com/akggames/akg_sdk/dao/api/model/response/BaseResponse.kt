package com.akggames.akg_sdk.dao.api.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
open class BaseResponse {

    var BaseDataResponse: DataBean? = null
    var BaseMetaResponse: MetaBean? = null

    class DataBean {
        var message: String? = null
        var token:String?=null
    }

    class MetaBean {
        var status: Boolean = false
        var code: Int = 0
    }
}
