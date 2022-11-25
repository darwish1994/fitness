package com.app.fitness.common.notification

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import com.app.fitness.R

object NotificationUtil {

    fun createStepsNotification(context: Context, steps: Int): Notification {
        val notification = NotificationCompat.Builder(
            context,
            context.getString(R.string.session_notification_channel_id)
        )
            .setContentTitle(context.getString(R.string.steps_tracking))
            .setContentText(context.getString(R.string.steps_number, steps))
            .setSmallIcon(R.drawable.ic_location)
            .setOngoing(true)

        return notification.build()

    }



}