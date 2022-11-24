package com.app.fitness.service.steps

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class StepsUpdateModel( private val context: Context) :
    StepsClient {
    override fun getStepsUpdates(): Flow<Int> {
        return callbackFlow {

            val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            val stepsSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
            val stepsCallback = object : SensorEventListener{
                override fun onSensorChanged(p0: SensorEvent?) {
                    launch { send(p0?.values?.first()?.toInt()?:0) }
                }

                override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
                }
            }

            if (stepsSensor == null)
                throw StepsClient.StepException("No Step Counter Sensor")
            else {
               val register= sensorManager.registerListener(
                    stepsCallback,
                    stepsSensor,
                    SensorManager.SENSOR_DELAY_UI
                )

                if (!register)
                    throw StepsClient.StepException("fail to register step sensour")
            }

            awaitClose {
                sensorManager.unregisterListener(stepsCallback)
            }

        }
    }
}