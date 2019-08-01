package com.akggames.akg_sdk.dao.api


interface IApiManager {
     fun initApiService(
        apiDomain: String,
        allowUntrusted: Boolean,
        clazz: Class<IApiManager>,
        timeout: Int,
        enableLoggingHttp: Boolean
    ): Any
}