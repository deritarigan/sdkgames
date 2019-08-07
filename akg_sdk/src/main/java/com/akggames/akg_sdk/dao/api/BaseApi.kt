//package com.akggames.akg_sdk.dao.api
//
//import com.akggames.akg_sdk.IConfig
//
//private var apiDomain: String? = null
////private var baseApi: BaseApi? = null
//
//fun BaseApi() {}
//
//
//fun getApiDomain(): String? {
//    return apiDomain
//}
//
//fun setApiDomain(mApiDomain: String) {
//    apiDomain = mApiDomain
//}
//
//fun setupApi(clazz: Class<*>, enableLoggingHttp: Boolean): Any {
//    return setupApi(clazz, false, IConfig.TIMEOUT_SHORT_INSECOND, enableLoggingHttp)
//}
//
//
//fun setupApi(
//    clazz: Class<*>,
//    allowUntrusted: Boolean,
//    timeout: Int,
//    enableLoggingHttp: Boolean
//): Any {
//    val api = appComponent.getApi()
//
//    return api.initApiService(getApiDomain(), allowUntrusted, clazz, timeout, enableLoggingHttp)
//}
//
//
//fun setupApiDomain(
//    domain: String,
//    clazz: Class<*>,
//    allowUntrusted: Boolean,
//    timeout: Int,
//    enableLoggingHttp: Boolean
//): Any {
//    this.apiDomain = domain
//    return appComponent.getApi().initApiService(domain, allowUntrusted, clazz, timeout, enableLoggingHttp)
//}