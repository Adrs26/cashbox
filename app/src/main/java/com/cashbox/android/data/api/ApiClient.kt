package com.cashbox.android.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    val apiClient: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://cashbox-377e3.et.r.appspot.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}