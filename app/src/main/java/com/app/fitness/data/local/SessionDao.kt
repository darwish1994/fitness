package com.app.fitness.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.app.fitness.domain.model.Tracking
import com.app.fitness.domain.model.Session
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {

    @Insert(onConflict = REPLACE)
    suspend fun saveTrip(trip: Session)

    @Update
    suspend fun updateTrip(trip: Session)

    @Insert(onConflict = REPLACE)
    suspend fun saveLocation(tracking: Tracking)

    @Query("SELECT * FROM session")
    suspend fun getTrips(): List<Session>

    @Query("SELECT * FROM tracking where tripId=:tripId")
    suspend fun getTracking(tripId: Int): List<Tracking>

    @Query("SELECT * FROM session ORDER BY id desc LIMIT 1")
    suspend fun getLastTrip(): Session?

    @Query("SELECT * FROM session ORDER BY id desc LIMIT 1")
     fun getCurrentTrip(): Flow<Session?>




}