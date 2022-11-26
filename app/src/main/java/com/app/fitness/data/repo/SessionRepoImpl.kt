package com.app.fitness.data.repo

import com.app.fitness.data.local.SessionDao
import com.app.fitness.data.local.SessionTracking
import com.app.fitness.domain.model.Session
import com.app.fitness.domain.model.Status
import com.app.fitness.domain.model.Tracking
import com.app.fitness.domain.repo.SessionRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SessionRepoImpl @Inject constructor(private val trackingDao: SessionDao) : SessionRepo {

    /**
     * create new trip and make it status [Status.START]
     * save it in data base
     * */
    override suspend fun startTrip() {
        trackingDao.saveTrip(
            Session(
                status = Status.START
            )
        )
    }

    /**
     * pause last trip in data base
     * */
    override suspend fun pauseTrip() {
        val trip = trackingDao.getLastSession()
        trip?.apply {
            if (status == Status.START) {
                status = Status.PAUSE
                trackingDao.updateTrip(this)
            }
        }

    }

    /**
     *
     * resume trip after pause
     * */
    override suspend fun resumeTrip() {
        val trip = trackingDao.getLastSession()
        trip?.apply {
            if (status == Status.PAUSE) {
                status = Status.START
                trackingDao.updateTrip(this)
            }
        }
    }


    /**
    end trip and save duration
     *
     **/


    override suspend fun endTrip(duration: Long) {
        val trip = trackingDao.getLastSession()
        trip?.apply {
            if (status != Status.FINISHED) {
                status = Status.FINISHED
                this.duration = duration
                trackingDao.updateTrip(this)
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
                trackingDao.updateTrip(this)
            }
        }
    }

    override suspend fun updateTripLocation(latitude: Double, longitude: Double) {
        val trip = trackingDao.getLastSession()
        trip?.apply {
            if (status != Status.FINISHED) {
                trackingDao.saveLocation(
                    Tracking(
                        tripId = id!!,
                        latitude = latitude,
                        longitude = longitude
                    )
                )

            }
        }
    }



    override fun getCurrentTripUpdates(): Flow<Session?> = trackingDao.getCurrentSession()

    /**
     * get all complete sessions
     * */
    override  fun getAllFinishTrips(): Flow<List<Session>> = trackingDao.getSessions(Status.FINISHED)

    override suspend fun getSessionDetails(id: Int): SessionTracking = trackingDao.getSessionDetails(id)
}