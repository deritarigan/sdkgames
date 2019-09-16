package com.akggame.akg_sdk.dao.api.model.response

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class CurrentUserResponse : BaseResponse() {


    var data: DataBean? = null
    var meta: BaseResponse.MetaBean? = null

    @JsonIgnoreProperties(ignoreUnknown = true)
    class DataBean {

        var id: String? = null
        var type: String? = null
        var attributes: AttributesBean? = null
        var message: String? = null

        @JsonIgnoreProperties(ignoreUnknown = true)
        class AttributesBean {
            var uid: String? = null
            var email: String? = null
            var phone_number: String? = null
            var auth_provider: String? = null
            var created_at: String? = null
            var game_provider: String? = null
        }
    }
}
