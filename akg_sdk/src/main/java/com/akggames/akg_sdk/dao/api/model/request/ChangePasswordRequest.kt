package com.akggames.akg_sdk.dao.api.model.request

class ChangePasswordRequest {

    var old_password: String? = null
    var password: String? = null
    var password_confirmation: String? = null
}