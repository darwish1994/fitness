package com.app.fitness.domain.usecase

import com.app.fitness.domain.repo.SessionRepo
import javax.inject.Inject

class SessionDetailsUseCase @Inject constructor(private val sessionRepo: SessionRepo) {

    suspend operator fun invoke(id: Int) = sessionRepo.getSessionDetails(id)


}