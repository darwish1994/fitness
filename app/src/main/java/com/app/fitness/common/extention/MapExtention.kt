package com.app.fitness.common.extention

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

fun GoogleMap.applyMapCamera(latLng: LatLng, zoom:Float) {
    val cameraPosition = CameraPosition.Builder()
        .target(latLng) // Sets the center of the map
        .zoom(zoom) // Sets the zoom
        .build()
    this.animateCamera(
        CameraUpdateFactory.newCameraPosition(cameraPosition)
    )
}
