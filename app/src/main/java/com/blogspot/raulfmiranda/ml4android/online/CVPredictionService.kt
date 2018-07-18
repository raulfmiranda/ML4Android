package com.blogspot.raulfmiranda.ml4android.online

import com.blogspot.raulfmiranda.ml4android.model.CVPrediction
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface CVPredictionService {
    @Multipart
    @POST("customvision/v2.0/Prediction/a0f0d075-7f37-4dea-a2ca-3a3b034f594b/image")
    fun makePrediction(
            @HeaderMap headers: Map<String, String>,
            @Query("iterationId") iterationId: String,
            @Part("pulmao.jpeg") image: RequestBody
    ): Call<CVPrediction>
}


