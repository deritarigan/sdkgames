package com.akggame.akg_sdk.dao.api.model.request

data class PhoneBindingRequest(
    val device_id: String,
    val operating_system: String,
    val password: String,
    val phone_model: String,
    val phone_number: String
)

