package com.akggame.akg_sdk.dao.api.model.response

import androidx.annotation.Keep

class BannerResponse : BaseResponse() {


    var meta: BaseResponse.MetaBean? = null
    var data: List<DataBean>? = null

    class DataBean {

        var id: String? = null
        var type: String? = null
        var attributes: AttributesBean? = null

        class AttributesBean {

            var title: String? = null
            var image_link_url: String? = null
            var cover_image: CoverImageBean? = null
            var game: GameBean? = null

            class CoverImageBean {

                var url: String? = null
                var thumb: ThumbBean? = null

                class ThumbBean {

                    var url: String? = null
                }
            }

            class GameBean {

                var id: String? = null
                var name: String? = null
                var platform: String? = null
                var product_code: String? = null
            }
        }
    }
}
