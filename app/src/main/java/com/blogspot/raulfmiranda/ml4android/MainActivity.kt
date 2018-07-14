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
import android.os.Environment
import android.widget.Toast
import com.blogspot.raulfmiranda.ml4android.util.FileFromBitmap
import java.io.File
import com.blogspot.raulfmiranda.ml4android.async.CustomVisionAPI
import com.blogspot.raulfmiranda.ml4android.util.Permission
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), Callback<CVPrediction?>, FileFromBitmap.AsyncResponse {

//    private val imgFilePath: String = Environment.getExternalStorageDirectory().path + File.separator + "pulmao.jpg"
//    private var path = Environment.getExternalStorageDirectory().path
    private var path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path
    private var fileName = "pulmao.jpeg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bitmapPerson1virus7 = BitmapFactory.decodeResource(resources, R.drawable.person1_virus_7)

        btnSendImg.setOnClickListener {
            makeFileFromBitmap(this@MainActivity, bitmapPerson1virus7, path, fileName)
        }

        Permission.checkPermission(this@MainActivity)
    }

    private fun makeFileFromBitmap(asyncResponse: FileFromBitmap.AsyncResponse, bitmap: Bitmap, path: String, fileName: String) {
        val fileFromBitmap = FileFromBitmap(asyncResponse, bitmap, path, fileName)
        fileFromBitmap.execute()
    }

    override fun fileFromBitmapFinished(file: File?) {

        file?.let {
            CustomVisionAPI.makePrediction(this@MainActivity, it)


//            val intent = Intent(Intent.ACTION_VIEW)
//            val uri = Uri.fromFile(it)
//            intent.setDataAndType(uri, "image/jpeg")
//            startActivity(intent)
        }
    }

    override fun onFailure(call: Call<CVPrediction?>?, t: Throwable?) {
        val erroMsg = t?.message.toString()
        txtPrediction.text = erroMsg
    }

    override fun onResponse(call: Call<CVPrediction?>?, response: Response<CVPrediction?>?) {
        response?.body()?.let {
            if(it.predictions.size > 0) {
                val pred = "${it.predictions[0].tagName}: ${it.predictions[0].probability}"
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
                } else {
                    Toast.makeText(this@MainActivity, "Permission Denied!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
