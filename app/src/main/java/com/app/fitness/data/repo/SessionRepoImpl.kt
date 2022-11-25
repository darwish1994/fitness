package com.app.fitness.data.repo

import android.location.Location
import com.app.fitness.data.local.SessionDao
import com.app.fitness.domain.model.Status
import com.app.fitness.domain.model.Session
import com.app.fitness.domain.model.Tracking
import com.app.fitness.domain.repo.SessionRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
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
        val trip = trackingDao.getLastTrip()
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
        val trip = trackingDao.getLastTrip()
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
        val trip = trackingDao.getLastTrip()
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
        val trip = trackingDao.getLastTrip()
        trip?.apply {
            if (status != Status.FINISHED) {
                this.steps = steps
                trackingDao.updateTrip(this)
            }
        }
    }

    override suspend fun updateTripLocation(latitude: Double, longitude: Double) {
        val trip = trackingDao.getLastTrip()
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


    private suspend fun calculatedDistance(
        lat1: Double,
        long1: Double,
        lat2: Double,
        long2: Double
    ): Float {
        return withContext(Dispatchers.Default) {
            Location("").apply {
                latitude = lat1
                longitude = long1
            }.distanceTo(
                Location("").apply {
                    latitude = lat2
                    longitude = long2
                }
            )
        }
    }


    override fun getCurrentTripUpdates(): Flow<Session?> = trackingDao.getCurrentTrip()

    /**
     * get all complete sessions
     * */
    override  fun getAllFinishTrips(): Flow<List<Session>> = trackingDao.getSessions(Status.FINISHED)
}