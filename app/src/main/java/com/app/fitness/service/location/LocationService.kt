package com.app.fitness.service.location

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.app.fitness.common.extention.stepNotify
import com.app.fitness.common.notification.NotificationUtil
import com.app.fitness.domain.usecase.UpdateSessionLocationUseCase
import com.app.fitness.domain.usecase.UpdateSessionStepsUseCase
import com.app.fitness.service.steps.StepsClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
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

    private var lastStepResult: Int? = null


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

        locationClient.getLocationUpdates(20 * 1000L)
            .catch { e -> e.printStackTrace() }
            .onEach { location ->
                // save location to data base
                updateSessionLocationUseCase.invoke(location)

            }
            .launchIn(serviceScope)

        stepClient.getStepsUpdates().catch { e ->
            e.printStackTrace()
        }.onEach {
            if (lastStepResult == null)
                lastStepResult = it
            updateSessionStepsUseCase.invoke(it - lastStepResult!!)

            NotificationUtil.createStepsNotification(this, it - lastStepResult!!).stepNotify(this)

        }.launchIn(serviceScope)


        startForeground(1, NotificationUtil.createStepsNotification(this, 0))
    }

    private fun stop() {
        lastStepResult = null
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