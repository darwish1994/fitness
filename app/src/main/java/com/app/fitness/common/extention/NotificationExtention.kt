package com.app.fitness.common.extention

import android.app.Notification
import android.app.NotificationManager
import android.content.Context

fun Notification.stepNotify(context: Context) {
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(1, this)
}