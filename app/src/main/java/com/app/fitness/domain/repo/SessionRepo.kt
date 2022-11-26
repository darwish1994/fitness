package com.app.fitness.domain.repo

import com.app.fitness.data.local.TrackingSession
import com.app.fitness.data.model.Session
import kotlinx.coroutines.flow.Flow

interface SessionRepo {

    suspend fun startSession()

    suspend fun pauseSession()

    suspend fun resumeSession()

    suspend fun endSession(duration: Long)

    suspend fun updateTripSteps(steps: Int)

    suspend fun updateSessionLocation(latitude: Double, longitude: Double)

    fun getCurrentSessionUpdates(): Flow<Session?>

    fun getAllFinishSession(): Flow<List<Session>>

    suspend fun getSessionDetails(id: Int): TrackingSession


}