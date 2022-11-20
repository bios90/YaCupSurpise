package com.bios.yacupsurpise.di

import androidx.lifecycle.viewModelScope
import com.bios.yacupsurpise.base.Consts
import com.bios.yacupsurpise.base.gsonGlobal
import com.bios.yacupsurpise.base.view_models.BaseViewModel
import com.bios.yacupsurpise.networking.BaseNetworker
import com.bios.yacupsurpise.networking.api.Api
import com.grapesnberries.curllogger.CurlLoggerInterceptor
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object BaseInjector {

    private val httpClient: OkHttpClient by lazy {
        val client_builder = OkHttpClient.Builder()
            .connectTimeout(500, TimeUnit.SECONDS)
            .readTimeout(500, TimeUnit.SECONDS)
            .writeTimeout(500, TimeUnit.SECONDS)
            .callTimeout(500, TimeUnit.SECONDS)

        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 10
        client_builder.dispatcher(dispatcher)
        client_builder.addInterceptor(CurlLoggerInterceptor("**** curl ****"))

        client_builder.build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Consts.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gsonGlobal))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .build()
    }

    private val api: Api by lazy {
        retrofit.create(Api::class.java)
    }

    fun provideBaseNetworker(vm: BaseViewModel<*, *>) = BaseNetworker(
        scope = vm.viewModelScope,
        api = api
    )
}