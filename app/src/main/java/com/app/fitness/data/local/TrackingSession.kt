package com.app.fitness.data.local

import androidx.room.Embedded
import androidx.room.Relation
import com.app.fitness.data.model.Session
import com.app.fitness.data.model.Tracking

data class TrackingSession(
    @Embedded
    val session: Session,
    @Relation(parentColumn = "id", entityColumn = "tripId")
    val locations: List<Tracking>
)