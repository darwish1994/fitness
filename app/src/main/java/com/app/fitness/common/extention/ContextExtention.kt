package com.app.fitness.common.extention

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.app.fitness.R
import com.app.fitness.service.location.LocationService
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

fun Context.hasLocationPermission(): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
}

fun Context.startLocationTracker() {
    Intent(applicationContext, LocationService::class.java).apply {
        action = LocationService.ACTION_START
        startService(this)
    }
}

fun Context.stopLocationTracker() {
    Intent(applicationContext, LocationService::class.java).apply {
        action = LocationService.ACTION_STOP
        stopService(this)
    }
}



fun Context.checkPermissions(permissions: List<String>, granted: () -> Unit, denied: () -> Unit) {
    Dexter.withContext(this).withPermissions(permissions).withListener(object :
        MultiplePermissionsListener {
        override fun onPermissionsChecked(p0: MultiplePermissionsReport) {
            if (p0.areAllPermissionsGranted())
                granted.invoke()
            else
                denied.invoke()

        }

        override fun onPermissionRationaleShouldBeShown(
            p0: MutableList<PermissionRequest>?,
            p1: PermissionToken?
        ) {
            p1?.continuePermissionRequest()
        }

    }).onSameThread().check()


}


fun Context.createLocationNotificationChannel(name:String,description:String,channelId:String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val importance = NotificationManager.IMPORTANCE_LOW
        ( getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
            NotificationChannel(channelId, name, importance).apply {
                this.description = description
                enableLights(true)
                enableVibration(true)
            }
        )

    }
}

fun Context.showToast(@StringRes msg:Int){
    Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
}