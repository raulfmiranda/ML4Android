package com.blogspot.raulfmiranda.ml4android.async

import com.blogspot.raulfmiranda.ml4android.model.CVPrediction
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface CVPredictionService {
    @Multipart
    @POST("customvision/v2.0/Prediction/a0f0d075-7f37-4dea-a2ca-3a3b034f594b/image")
    fun makePrediction(
            @HeaderMap headers: Map<String, String>,
            @Query("iterationId") iterationId: String,
            @Part image: MultipartBody.Part
    ): Call<CVPrediction>
}


