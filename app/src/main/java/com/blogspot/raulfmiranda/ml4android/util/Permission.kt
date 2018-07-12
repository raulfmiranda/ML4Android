package com.blogspot.raulfmiranda.ml4android.util

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast

class Permission {

    companion object {
        private val WRITE_EXTERNAL_STORAGE_CODE = 1


        fun askUserPermission(activity: Activity) {
            ActivityCompat.requestPermissions(activity,
                    Array<String>(1){ Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE_CODE)
        }

        fun checkPermission(activity: Activity, bitmap: Bitmap) {
            if (ContextCompat.checkSelfPermission(activity,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                    // TODO:Apenas copiei o mesmo que está no ELSE abaixo.
                    Toast.makeText(activity, "You must accept permission", Toast.LENGTH_LONG).show()
                    askUserPermission(activity)

                } else {

                    // No explanation needed, we can request the permission.

                    askUserPermission(activity)

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }
            else {
                makeFileFromBitmap(activity as FileFromBitmap.AsyncResponse, bitmap)
            }
        }

        private fun makeFileFromBitmap(asyncResponse: FileFromBitmap.AsyncResponse, bitmap: Bitmap) {
            val fileFromBitmap = FileFromBitmap(asyncResponse, bitmap)
            fileFromBitmap.execute()
        }
    }
}