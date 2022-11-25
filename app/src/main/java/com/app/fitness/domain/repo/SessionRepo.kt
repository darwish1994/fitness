package com.app.fitness.domain.repo

import com.app.fitness.domain.model.Session
import kotlinx.coroutines.flow.Flow

interface SessionRepo {

    suspend fun startTrip()

    suspend fun pauseTrip()

    suspend fun resumeTrip()

    suspend fun endTrip(duration: Long)

    suspend fun updateTripDistance(distance: Double)

    suspend fun updateTripSteps(steps: Int)

    fun getCurrentTripUpdates(): Flow<Session?>

    suspend fun getAllFinishTrips():List<Session>


}