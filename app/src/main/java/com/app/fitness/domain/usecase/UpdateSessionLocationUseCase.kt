package com.app.fitness.domain.usecase

import android.location.Location
import com.app.fitness.domain.repo.SessionRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateSessionLocationUseCase @Inject constructor(private val sessionRepo: SessionRepo) {

    suspend operator fun invoke(location: Location) {
        sessionRepo.updateTripLocation(latitude = location.latitude, longitude = location.longitude)
    }

}