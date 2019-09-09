package com.akggame.akg_sdk.dao.api.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class SDKConfigResponse : BaseResponse() {

    var data: DataBean? = null
    var meta: BaseResponse.MetaBean? = null

    @JsonIgnoreProperties(ignoreUnknown = true)
    class DataBean {

        var adjust: AdjustBean? = null

        @JsonIgnoreProperties(ignoreUnknown = true)
        class AdjustBean {

            var app_token: String? = null
            var events: List<*>? = null
        }
    }
}
