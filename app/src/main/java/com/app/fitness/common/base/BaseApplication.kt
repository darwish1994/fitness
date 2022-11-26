package com.app.fitness.common.base

import android.app.Application
import com.app.fitness.R
import com.app.fitness.common.extention.createLocationNotificationChannel
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // create session notificationChannel
        createLocationNotificationChannel(
            name = getString(R.string.session_notification_channel_name),
            description = getString(R.string.session_channel_description),
            channelId = getString(R.string.session_notification_channel_id)
        )


    }
}