package com.app.fitness.presenter.details

import androidx.lifecycle.ViewModel
import com.app.fitness.data.local.TrackingSession
import com.app.fitness.domain.usecase.SessionDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val sessionDetailsUseCase: SessionDetailsUseCase
) : ViewModel() {


    suspend fun getSession(id: Int): TrackingSession =
        withContext(Dispatchers.IO) {
            sessionDetailsUseCase.invoke(id)
        }
}