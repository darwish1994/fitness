package com.app.fitness.domain.usecase

import com.app.fitness.domain.repo.SessionRepo
import javax.inject.Inject

class SessionsUseCase @Inject constructor(private val sessionRepo: SessionRepo) {

     operator fun invoke() = sessionRepo.getAllFinishSession()


}