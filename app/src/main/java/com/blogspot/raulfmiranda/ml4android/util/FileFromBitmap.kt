package com.blogspot.raulfmiranda.ml4android.util

import android.content.Context
import android.os.AsyncTask
import android.graphics.Bitmap
import android.os.Environment
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import android.app.Activity
import java.lang.ref.WeakReference


class FileFromBitmap() : AsyncTask<Void, Void, File>() {

    private var path = Environment.getExternalStorageDirectory().path
    private val fileName = "pulmao.jpeg"
//    private var path = Environment.getExternalStorageDirectory().path + "/pulmao.jpg"
//    private var path = Environment.getExternalStorageDirectory().path + File.separator + "pulmao.jpg"
    private var bitmap: Bitmap? = null
    var delegate: AsyncResponse? = null

    constructor(delegate: AsyncResponse, bitmap: Bitmap) : this() {
        this.delegate = delegate
        this.bitmap = bitmap
    }

    interface AsyncResponse {
        fun fileFromBitmapFinished(file: File?)
    }

    override fun onPreExecute() {
        super.onPreExecute()
        // before executing doInBackground
        // update your UI
        // exp; make progressbar visible
    }

    override fun doInBackground(vararg p0: Void?): File {
        val bytes = ByteArrayOutputStream()
//        val file = File(path)
        val file = File(path, fileName)
        file.createNewFile()

        bitmap?.let {
            val bos = ByteArrayOutputStream()
            it.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val bitmapdata = bos.toByteArray()

            try {
                val fo = FileOutputStream(file)
//                fo.write(bytes.toByteArray())
                fo.write(bitmapdata)
                fo.flush()
                fo.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return file
    }


    override fun onPostExecute(result: File?) {
        super.onPostExecute(result)
        // back to main thread after finishing doInBackground
        // update your UI or take action after
        // exp; make progressbar gone

        delegate?.fileFromBitmapFinished(result)
    }
}