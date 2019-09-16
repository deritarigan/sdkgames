package com.akggame.akg_sdk.dao.api.model.response

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class SDKVersionResponse : BaseResponse() {

    var data: DataBean? = null
    var meta: BaseResponse.MetaBean? = null

    @JsonIgnoreProperties(ignoreUnknown = true)
    class DataBean {

        var id: String? = null
        var type: String? = null
        var attributes: AttributesBean? = null

        @JsonIgnoreProperties(ignoreUnknown = true)
        class AttributesBean {

            var version_name: String? = null
            var version_number: Int = 0
            var status: String? = null
            var game: String? = null
        }
    }
}
