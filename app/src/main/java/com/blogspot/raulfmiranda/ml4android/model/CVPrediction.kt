package com.blogspot.raulfmiranda.ml4android.model


data class CVPrediction(
        val id: String,
        val project: String,
        val iteration: String,
        val created: String,
        val predictions: List<Prediction>
) {

    data class Prediction(
            val probability: Double,
            val tagId: String,
            val tagName: String
    )
}