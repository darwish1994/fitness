package com.app.fitness.presenter.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.fitness.domain.model.Session
import com.app.fitness.domain.usecase.SessionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val sessionsUseCase: SessionsUseCase
) :ViewModel() {

    val sessionsLiveData by lazy {MutableLiveData<List<Session>>()  }

    fun getSessions(){
        sessionsUseCase.invoke().onEach {
            sessionsLiveData.value= it
        }.launchIn(viewModelScope)


    }


}