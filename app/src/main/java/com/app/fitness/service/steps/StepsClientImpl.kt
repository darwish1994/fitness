package com.app.fitness.service.steps

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import com.app.fitness.R
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

class StepsClientImpl @Inject constructor(private val context: Context) : StepsClient {

    override fun getStepsUpdates(): Flow<Int> {
        return callbackFlow {
            val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            val stepsSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)


            val stepsCallback = object : SensorEventListener {
                override fun onSensorChanged(p0: SensorEvent?) {
                    p0?.values?.first()?.let {
                        launch { send(it.toInt()) }
                    }

                }

                override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

                }
            }

            if (stepsSensor == null)
                throw StepsClient.StepException(context.getString(R.string.no_sensor))
            else {
                val register = sensorManager.registerListener(
                    stepsCallback,
                    stepsSensor,
                    SensorManager.SENSOR_DELAY_UI
                )

                if (!register)
                    throw StepsClient.StepException(context.getString(R.string.fail_to_register_sensor))


            }

            awaitClose {
                sensorManager.unregisterListener(stepsCallback,stepsSensor)
            }

        }
    }
}