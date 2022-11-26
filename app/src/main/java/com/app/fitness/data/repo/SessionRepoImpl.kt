package com.app.fitness.data.repo

import com.app.fitness.data.local.SessionDao
import com.app.fitness.data.local.TrackingSession
import com.app.fitness.data.model.Session
import com.app.fitness.data.model.Status
import com.app.fitness.data.model.Tracking
import com.app.fitness.domain.repo.SessionRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SessionRepoImpl @Inject constructor(private val trackingDao: SessionDao) : SessionRepo {

    /**
     * create new trip and make it status [Status.START]
     * save it in data base
     * */
    override suspend fun startSession() {
        trackingDao.saveSession(
            Session(
                status = Status.START
            )
        )
    }

    /**
     * pause last trip in data base
     * */
    override suspend fun pauseSession() {
        val trip = trackingDao.getLastSession()
        trip?.apply {
            if (status == Status.START) {
                status = Status.PAUSE
                trackingDao.updateSession(this)
            }
        }

    }

    /**
     *
     * resume trip after pause
     * */
    override suspend fun resumeSession() {
        val trip = trackingDao.getLastSession()
        trip?.apply {
            if (status == Status.PAUSE) {
                status = Status.START
                trackingDao.updateSession(this)
            }
        }
    }


    /**
    end trip and save duration
     *
     **/


    override suspend fun endSession(duration: Long) {
        val trip = trackingDao.getLastSession()
        trip?.apply {
            if (status != Status.FINISHED) {
                status = Status.FINISHED
                this.duration = duration
                trackingDao.updateSession(this)
            }
        }
    }


    /***
     *
     * set trip steps
     * */
    override suspend fun updateTripSteps(steps: Int) {
        val trip = trackingDao.getLastSession()
        trip?.apply {
            if (status != Status.FINISHED) {
                this.steps = steps
                trackingDao.updateSession(this)
            }
        }
    }

    override suspend fun updateSessionLocation(latitude: Double, longitude: Double) {
        val trip = trackingDao.getLastSession()
        trip?.apply {
            if (status != Status.FINISHED && id != null) {
                trackingDao.saveLocation(
                    Tracking(
                        tripId = id,
                        latitude = latitude,
                        longitude = longitude
                    )
                )

            }
        }
    }


    override fun getCurrentSessionUpdates(): Flow<Session?> = trackingDao.getCurrentSession()

    /**
     * get all complete sessions
     * */
    override fun getAllFinishSession(): Flow<List<Session>> = trackingDao.getSessions(Status.FINISHED)

    override suspend fun getSessionDetails(id: Int): TrackingSession =
        trackingDao.getSessionDetails(id)
}