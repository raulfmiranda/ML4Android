package com.blogspot.raulfmiranda.ml4android.online

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer(baseUrl: String) {
    private val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun cvPredictionService() = retrofit.create(CVPredictionService::class.java)
}