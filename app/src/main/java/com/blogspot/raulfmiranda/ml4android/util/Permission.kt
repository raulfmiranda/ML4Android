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
        private val PERMISSION_ALL = 1
        private val PERMISSIONS = arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET)

        fun checkPermission(activity: Activity): Boolean {
            var isPermissionGranted = false

            for(P: String in PERMISSIONS) {
                isPermissionGranted =
                        ContextCompat.checkSelfPermission(activity, P) == PackageManager.PERMISSION_GRANTED

                if(!isPermissionGranted) break
            }

            return isPermissionGranted
        }

        fun requestPermissions(activity: Activity) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS, PERMISSION_ALL)
        }
    }
}

