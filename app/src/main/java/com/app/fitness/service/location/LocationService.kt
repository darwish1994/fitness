package com.app.fitness.service.location

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.app.fitness.R
import com.app.fitness.domain.usecase.UpdateSessionLocationUseCase
import com.app.fitness.domain.usecase.UpdateSessionStepsUseCase
import com.app.fitness.service.steps.StepsClient
import com.app.fitness.service.steps.StepsClientImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class LocationService : Service() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)


    @Inject
    lateinit var locationClient: LocationClient

    @Inject
    lateinit var stepClient: StepsClient

    @Inject
    lateinit var updateSessionStepsUseCase: UpdateSessionStepsUseCase

    @Inject
    lateinit var updateSessionLocationUseCase: UpdateSessionLocationUseCase


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        start()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
//            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        Log.v("LocationService","start service")
        val notification = NotificationCompat.Builder(
            this,
            resources.getString(R.string.notification_location_channel_id)
        )
            .setContentTitle(getString(R.string.location_tracking))
            .setContentText(getString(R.string.search_for_location))
            .setSmallIcon(R.drawable.ic_location)
            .setOngoing(true)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        locationClient.getLocationUpdates(10 * 1000L)
            .catch { e -> e.printStackTrace() }
            .onEach { location ->

                // save location to data base
                updateSessionLocationUseCase.invoke(location)

                val lat = location.latitude.toString()
                val long = location.longitude.toString()

                val updatedNotification = notification.setContentText("Location: ($lat, $long)")
                notificationManager.notify(1, updatedNotification.build())

            }
            .launchIn(serviceScope)

        stepClient.getStepsUpdates().catch { e ->
            e.printStackTrace()
        }.onEach {
            updateSessionStepsUseCase.invoke(it)
            Log.v("LocationService","steps:$it")

        }.launchIn(serviceScope)


        startForeground(1, notification.build())
    }

    private fun stop() {
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }
}