package com.app.fitness.common.extention

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.location.Location
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.app.fitness.common.base.FragmentViewBindingDelegate
import com.app.fitness.domain.model.Tracking
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

// observer
fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))


// activity delegation
inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T
) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }

// fragment delegation
fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T) =
    FragmentViewBindingDelegate(this, viewBindingFactory)




fun Int.getDistanceCovered(): String {
    val feet = (this * 2.5).toInt()
    val distance = feet / 3.281
    val finalDistance: Double = String.format("%.2f", distance).toDouble()
    return "$finalDistance"
}

fun Long.timerFormat(): String {
    val sec = (this / 1000) % 60
    val min = (this / (1000 * 60)) % 60
    val hour = (this / (1000 * 60 * 60)) % 24

    return String.format("%02d:%02d:%02d", hour, min, sec)
}



