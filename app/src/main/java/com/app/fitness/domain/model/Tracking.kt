package com.app.fitness.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tracking(
    @PrimaryKey(autoGenerate = true)
    val id: Int?=null,
    val tripId: Int,
    val latitude: Double,
    val longitude: Double
)