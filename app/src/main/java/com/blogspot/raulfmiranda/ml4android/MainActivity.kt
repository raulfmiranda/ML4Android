package com.blogspot.raulfmiranda.ml4android

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.blogspot.raulfmiranda.ml4android.model.CVPrediction
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Environment
import android.widget.Toast
import com.blogspot.raulfmiranda.ml4android.util.FileFromBitmap
import java.io.File
import com.blogspot.raulfmiranda.ml4android.async.CustomVisionAPI
import com.blogspot.raulfmiranda.ml4android.util.Permission
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), Callback<CVPrediction?>, FileFromBitmap.AsyncResponse {


    private var chosenImgFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnMakeOnlinePrediction.isEnabled = false
        btnMakeOnlinePrediction.setOnClickListener {

            txtPrediction.text = "Sending image to the server. Please, wait!"
            txtPrediction.background = ColorDrawable(Color.RED)
            txtPrediction.setTextColor(Color.WHITE)

            this.chosenImgFile?.let {
                CustomVisionAPI.makePrediction(this@MainActivity, it)
            }
        }

        btnChooseImg1.setOnClickListener {
            this.chosenImgFile?.let {
                if(it.exists()) {
                    val bitmap = BitmapFactory.decodeFile(it.absolutePath)
                    imgLoaded.setImageBitmap(bitmap)
                    btnMakeOnlinePrediction.isEnabled = true
                }
            }
        }

        val isPermissionGranted = Permission.checkPermission(this@MainActivity)
        if(isPermissionGranted) {
            makeFileFromBitmap1(this@MainActivity)
        } else {
            Permission.requestPermissions(this@MainActivity)
        }
    }

    private fun makeFileFromBitmap1(asyncResponse: FileFromBitmap.AsyncResponse) {
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path
        val fileName = "person1_virus_7.jpeg"
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.person1_virus_7)
        val fileFromBitmap = FileFromBitmap(asyncResponse, bitmap, path, fileName)
        fileFromBitmap.execute()
    }

    override fun fileFromBitmapFinished(file: File?) {

        file?.let {
            this.chosenImgFile = it
            btnChooseImg1.text = it.name
        }
    }

    // Custom Vison API onFailure
    override fun onFailure(call: Call<CVPrediction?>?, t: Throwable?) {
        val erroMsg = t?.message.toString()
        txtPrediction.text = erroMsg
    }

    // Custom Vison API onResponse
    override fun onResponse(call: Call<CVPrediction?>?, response: Response<CVPrediction?>?) {
        response?.body()?.let {
            if(it.predictions.size > 0) {
                var pred = ""
                val probX100 = it.predictions[0].probability * 100
                val prob = "%.1f".format(probX100)
                when (it.predictions[0].tagName) {
                    "pneumonia" -> pred = "$prob% chance of being pneumonia."
                    "normal" -> pred = "$prob% chance of not being pneumonia (normal)."
                    "bacteria" -> pred = "$prob% chance of being pneumonia cause by bacteria."
                    "virus" -> pred = "$prob% chance of being pneumonia cause by virus."
                }

                btnMakeOnlinePrediction.isEnabled = true
                txtPrediction.background = ColorDrawable(Color.BLUE)
                txtPrediction.text = pred
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        val PERMISSION_ALL = 1
        when(requestCode) {
            PERMISSION_ALL -> {
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this@MainActivity, "Permission Granted!", Toast.LENGTH_SHORT).show()

                    makeFileFromBitmap1(this@MainActivity)

                } else {
                    Toast.makeText(this@MainActivity, "Permission Denied!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
