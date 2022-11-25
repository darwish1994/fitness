package com.app.fitness.service.steps

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.app.fitness.R
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class StepsClientImpl(private val context: Context) : StepsClient {
    private var isRuning = false
    override fun getStepsUpdates(): Flow<Int> {
        return callbackFlow {
            val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            val stepsSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
            val stepsCallback = object : SensorEventListener {
                override fun onSensorChanged(p0: SensorEvent?) {
                    launch { send(p0?.values?.first()?.toInt() ?: 0) }
                }

                override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
                }
            }

            if (stepsSensor == null)
                throw StepsClient.StepException(context.getString(R.string.no_sensor))
            else {
                if (!isRuning) {


                    isRuning = true

                    val register = sensorManager.registerListener(
                        stepsCallback,
                        stepsSensor,
                        SensorManager.SENSOR_DELAY_FASTEST
                    )

                    if (!register)
                        throw StepsClient.StepException(context.getString(R.string.fail_to_register_sensor))
                }

            }

            awaitClose {
                sensorManager.unregisterListener(stepsCallback)
            }

        }
    }
}