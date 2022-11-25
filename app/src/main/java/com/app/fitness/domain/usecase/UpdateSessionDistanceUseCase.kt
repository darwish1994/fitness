package com.app.fitness.domain.usecase

import com.app.fitness.domain.repo.SessionRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateSessionDistanceUseCase @Inject constructor(private val sessionRepo: SessionRepo) {

    suspend operator fun invoke(distance: Double) {
        sessionRepo.updateTripDistance(distance)
    }

}