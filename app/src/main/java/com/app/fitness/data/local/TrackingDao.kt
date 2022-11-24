package com.app.fitness.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.app.fitness.domain.model.Tracking
import com.app.fitness.domain.model.Trip

@Dao
interface TrackingDao {

    @Insert(onConflict = REPLACE)
    suspend fun saveTrip(trip: Trip)

    @Insert(onConflict = REPLACE)
    suspend fun saveLocation(tracking: Tracking)

    @Query("SELECT * FROM trip")
    suspend fun getTrips():List<Trip>

    @Query("SELECT * FROM tracking where tripId=:tripId")
    suspend fun getTracking(tripId:Int):List<Tracking>


}