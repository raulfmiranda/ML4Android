package com.blogspot.raulfmiranda.ml4android

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.blogspot.raulfmiranda.ml4android.model.CVPrediction
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.graphics.BitmapFactory
import android.os.Environment
import com.blogspot.raulfmiranda.ml4android.util.FileFromBitmap
import java.io.File
import android.content.Intent
import android.net.Uri
import com.blogspot.raulfmiranda.ml4android.util.Permission


class MainActivity : AppCompatActivity(), Callback<CVPrediction?>, FileFromBitmap.AsyncResponse {

    private val imgFilePath: String = Environment.getExternalStorageDirectory().path + File.separator + "pulmao.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bitmapNormal = BitmapFactory.decodeResource(resources, R.drawable.normal)
//        val bitmapVirus = BitmapFactory.decodeResource(resources, R.drawable.virus)
//        val bitmapBacteria = BitmapFactory.decodeResource(resources, R.drawable.bacteria)

        Permission.checkPermission(this@MainActivity, bitmapNormal)
    }

    override fun fileFromBitmapFinished(file: File?) {

        file?.let {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.setDataAndType(Uri.parse(it.path), "image/*")
            startActivity(intent)
        }
    }

    override fun onFailure(call: Call<CVPrediction?>?, t: Throwable?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onResponse(call: Call<CVPrediction?>?, response: Response<CVPrediction?>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
