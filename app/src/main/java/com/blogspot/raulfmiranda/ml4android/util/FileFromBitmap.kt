package com.blogspot.raulfmiranda.ml4android.util

import android.os.AsyncTask
import android.graphics.Bitmap
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class FileFromBitmap() : AsyncTask<Void, Void, File>() {

    private var path: String? = null
    private var fileName: String? = null
    private var bitmap: Bitmap? = null
    var delegate: AsyncResponse? = null

    constructor(delegate: AsyncResponse, bitmap: Bitmap, path: String, fileName: String) : this() {
        this.delegate = delegate
        this.bitmap = bitmap
        this.path = path
        this.fileName = fileName
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

        val file = File(path, fileName)

        bitmap?.let {

            try {
                file.createNewFile()
                val bos = ByteArrayOutputStream()
                it.compress(Bitmap.CompressFormat.JPEG, 100, bos)
                val bitmapdata = bos.toByteArray()
                val fo = FileOutputStream(file)
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