package com.app.fitness.presenter.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.fitness.domain.model.Session
import com.app.fitness.domain.model.Status
import com.app.fitness.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val startSessionUseCase: StartSessionUseCase,
    private val pauseSessionUseCase: PauseSessionUseCase,
    private val resumeSessionUseCase: ResumeSessionUseCase,
    private val finishSessionUseCase: FinishSessionUseCase,
    private val currentSessionUseCase: CurrentSessionUseCase
) : ViewModel() {
    val currentSessionLiveData by lazy { MutableLiveData<Session>() }

    fun getCurrentSession() {
        currentSessionUseCase.invoke()
            .filter { it?.status != Status.FINISHED }
            .onEach {
                currentSessionLiveData.value = it
            }.launchIn(viewModelScope)

    }

    fun startSession() {
        viewModelScope.launch {
            startSessionUseCase.invoke()
        }
    }

    fun pauseSession() {
        viewModelScope.launch {
            pauseSessionUseCase.invoke()
        }
    }

    fun resumeSession() {
        viewModelScope.launch {
            resumeSessionUseCase.invoke()
        }
    }


}