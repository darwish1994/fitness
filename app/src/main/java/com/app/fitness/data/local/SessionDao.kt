package com.app.fitness.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.app.fitness.domain.model.Tracking
import com.app.fitness.domain.model.Session
import com.app.fitness.domain.model.Status
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {

    @Insert(onConflict = REPLACE)
    suspend fun saveTrip(trip: Session)

    @Update
    suspend fun updateTrip(trip: Session)

    @Insert(onConflict = REPLACE)
    suspend fun saveLocation(tracking: Tracking)

    @Query("SELECT * FROM session where status=:status ORDER BY id desc")
     fun getSessions(status: Status): Flow<List<Session>>

    @Query("SELECT * FROM tracking where tripId=:tripId")
    suspend fun getTracking(tripId: Int): List<Tracking>

    @Query("SELECT * FROM tracking where tripId=:tripId ORDER BY id desc LIMIT 1")
    suspend fun getLastTracking(tripId: Int): Tracking?

    @Query("SELECT * FROM session ORDER BY id desc LIMIT 1")
    suspend fun getLastTrip(): Session?

    @Query("SELECT * FROM session ORDER BY id desc LIMIT 1")
     fun getCurrentTrip(): Flow<Session?>




}