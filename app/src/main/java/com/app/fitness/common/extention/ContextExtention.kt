package com.app.fitness.common.extention

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
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

fun Context.createLocationNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = getString(R.string.notification_location_channel_name)
        val descriptionText = getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_LOW
        val channelId = getString(R.string.notification_location_channel_id)
        ( getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
            NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
                enableLights(true)
                enableVibration(true)
            }
        )

    }
}

fun Context.checkPermissions(permissions: List<String>, granted: () -> Unit, denied: () -> Unit) {
    Dexter.withContext(this).withPermissions(permissions).withListener(object :
        MultiplePermissionsListener {
        override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
            if (p0?.areAllPermissionsGranted() == true)
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