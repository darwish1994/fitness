package com.app.fitness.common.base

import android.app.Application
import com.app.fitness.common.extention.createLocationNotificationChannel
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        createLocationNotificationChannel()
    }
}