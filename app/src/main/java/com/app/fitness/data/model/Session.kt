package com.app.fitness.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Session(
    @PrimaryKey(autoGenerate = true)
    val id: Int?=null,
    var steps: Int? = 0,
    var duration: Long? = 0,
    var status: Status? = null
)
