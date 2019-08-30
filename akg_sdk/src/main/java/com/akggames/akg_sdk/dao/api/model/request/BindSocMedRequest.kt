package com.akggames.akg_sdk.dao.api.model.request

data class BindSocMedRequest(
    val access_token: String?,
    val auth_provider: String,
    val device_id: String,
    val operating_system: String,
    val phone_model: String
)