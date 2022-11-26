package com.app.fitness.data.local

import androidx.room.Embedded
import androidx.room.Relation
import com.app.fitness.domain.model.Session
import com.app.fitness.domain.model.Tracking

data class SessionTracking(
    @Embedded
    val session: Session,
    @Relation(parentColumn = "id", entityColumn = "tripId")
    val locations: List<Tracking>
)