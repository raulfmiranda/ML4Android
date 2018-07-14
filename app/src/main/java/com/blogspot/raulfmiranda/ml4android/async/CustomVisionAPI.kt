package com.blogspot.raulfmiranda.ml4android.async

import android.util.Log
import com.blogspot.raulfmiranda.ml4android.BuildConfig
import com.blogspot.raulfmiranda.ml4android.model.CVPrediction
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Callback
import java.io.File
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileReader


class CustomVisionAPI {

    companion object {
        private val predictionKey = BuildConfig.PredictionKey
        private val baseUrl = "https://southcentralus.api.cognitive.microsoft.com/"
        private val iterationId = "de7940c6-8a92-4fed-9d48-3fbfdce3ce99"
        private val TAG = "mytag"

        fun makePrediction(callback: Callback<CVPrediction?>, image: File) {

            try {
                val headers = HashMap<String, String>()
                headers.put("Prediction-Key", BuildConfig.PredictionKey)
                headers.put("Content-Type", "application/octet-stream")

//            var imgReq = RequestBody.create(MediaType.parse("image/*"), image)

                val fis = FileInputStream(image)
                val bytes = fis.readBytes()
                fis.close()

//                val byteArray = image.readBytes()
                val imgReq = RequestBody.create(MediaType.parse("image/jpeg"), bytes)
                val body = MultipartBody.Part.createFormData("upload", image.name, imgReq)

                val call = RetrofitInitializer(baseUrl).cvPredictionService().makePrediction(headers, iterationId, body)
                call.enqueue(callback)

            } catch (e: Exception) {
                Log.d(TAG, "Erro Message: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}