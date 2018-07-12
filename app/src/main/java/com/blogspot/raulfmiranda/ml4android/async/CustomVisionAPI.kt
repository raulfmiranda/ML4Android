package com.blogspot.raulfmiranda.ml4android.async

import android.util.Log
import com.blogspot.raulfmiranda.ml4android.BuildConfig
import com.blogspot.raulfmiranda.ml4android.model.CVPrediction
import retrofit2.Callback

class CustomVisionAPI {

    companion object {
        private val predictionKey = BuildConfig.PredictionKey
        private val baseUrl = "https://southcentralus.api.cognitive.microsoft.com/"
        private val iterationId = "de7940c6-8a92-4fed-9d48-3fbfdce3ce99"
        private val TAG = "mytag"

        fun makePrediction(callback: Callback<CVPrediction?>) {

            val headers = HashMap<String, String>()
            headers.put("Prediction-Key", BuildConfig.PredictionKey)
            headers.put("Content-Type", "application/octet-stream")

            try {
//                val call = RetrofitInitializer(baseUrl).cvPredictionService().makePrediction(headers, iterationId)
//                call.enqueue(callback)

            } catch (e: Exception) {
                Log.d(TAG, "Erro Message: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}