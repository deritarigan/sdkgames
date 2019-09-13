package com.akggame.akg_sdk.dao.api.model.response

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@Keep
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
open class BaseResponse {

    @Keep
    @JsonProperty("data")
    var BaseDataResponse: DataBean? = null
    @Keep
    @JsonProperty("meta")
    var BaseMetaResponse: MetaBean? = null

    @Keep
    @JsonIgnoreProperties(ignoreUnknown = true)
    class DataBean {
        var message: String? = null
        var token: String? = null
    }
    @Keep
    @JsonIgnoreProperties(ignoreUnknown = true)
    class MetaBean {
        var status: Boolean = false
        var code: Int = 0
    }
}
