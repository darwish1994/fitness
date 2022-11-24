package com.app.fitness.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Trip(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val steps: Int? = 0,
    val distance: Float? = 0f,
    val duration: Long? = 0,
    val status: Status? = null
)
