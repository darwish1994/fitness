package com.app.fitness.presenter.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.fitness.common.extention.timerFormat
import com.app.fitness.domain.model.Session
import com.app.fitness.domain.model.Status
import com.app.fitness.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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
    val timerLiveData by lazy { MutableLiveData<String>() }

    private var timer = 0L
    private var timerJob: Job? = null

    fun getCurrentSession() {
        currentSessionUseCase.invoke()
            .onEach {
                currentSessionLiveData.value = it
            }.launchIn(viewModelScope)

    }


    fun startSession() {
        viewModelScope.launch {
            startSessionUseCase.invoke()
        }

    }


    /**
     * timer module for calculate active time
     *
     * **/
    fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch(Dispatchers.Default) {
            while (true) {
                delay(1000)
                timer += 1000

                timerLiveData.postValue(timer.timerFormat())
            }
        }
    }

    fun pauseTimer() {
        timerJob?.cancel()
    }

    fun resetTimer() {
        timerJob?.cancel()
        timer = 0
        timerLiveData.value = "00:00:00"
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

    fun endSession(){
        viewModelScope.launch {
            finishSessionUseCase.invoke(timer)
        }
    }

    fun getDistanceCovered(steps: Int): String {
        val feet = (steps * 2.5).toInt()
        val distance = feet / 3.281
        val finalDistance: Double = String.format("%.2f", distance).toDouble()
        return "$finalDistance "
    }


}