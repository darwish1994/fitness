package com.app.fitness.domain.usecase

import com.app.fitness.domain.repo.SessionRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateSessionStepsUseCase @Inject constructor(private val sessionRepo: SessionRepo) {

    suspend operator fun invoke(steps: Int) {
        sessionRepo.updateTripSteps(steps)
    }

}