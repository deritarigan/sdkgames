package com.akggames.akg_sdk.dao.api.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class GameProductsResponse:BaseResponse()  {

     var meta: MetaBean? = null
     var data: List<DataBean>? = null

    @JsonIgnoreProperties(ignoreUnknown = true)
    class DataBean {

        var id: String? = null
        var type: String? = null
        var attributes: AttributesBean? = null

        @JsonIgnoreProperties(ignoreUnknown = true)
        class AttributesBean {

            var name: String? = null
            var product_code: String? = null
            var description: String? = null
            var platform: String? = null
            var price: String? = null
            var status: String? = null
            var game: String? = null
        }
    }

}
