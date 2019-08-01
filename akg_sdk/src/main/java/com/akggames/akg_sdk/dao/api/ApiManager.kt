package com.akggames.akg_sdk.dao.api

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class ApiManager : IApiManager {
    private var mApi: Any? = null

    override fun initApiService(
        apiDomain: String,
        allowUntrusted: Boolean,
        clazz: Class<IApiManager>,
        timeout: Int,
        enableLoggingHttp: Boolean
    ): Any {
        val retrofit = Retrofit.Builder()
            .baseUrl(apiDomain)
            .addConverterFactory(JacksonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        mApi = retrofit.create(clazz)

        return mApi as Any
    }

}