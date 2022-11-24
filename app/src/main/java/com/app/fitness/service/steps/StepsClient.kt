package com.app.fitness.service.steps


interface StepsClient {
    fun getStepsUpdates():kotlinx.coroutines.flow.Flow<Int>
    class StepException(message: String): Exception()

}