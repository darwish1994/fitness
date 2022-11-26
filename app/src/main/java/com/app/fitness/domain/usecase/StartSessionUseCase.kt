package com.app.fitness.domain.usecase

import com.app.fitness.domain.repo.SessionRepo
import javax.inject.Inject

class StartSessionUseCase @Inject constructor(private val sessionRepo: SessionRepo) {

    suspend operator fun invoke() {
        sessionRepo.startSession()
    }

}