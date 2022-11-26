package com.app.fitness.presenter.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.fitness.data.model.Session
import com.app.fitness.domain.usecase.SessionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val sessionsUseCase: SessionsUseCase
) :ViewModel() {

   private val sessionsLiveData by lazy {MutableLiveData<List<Session>>()  }

    fun getSessionsLiveData():LiveData<List<Session>> = sessionsLiveData

    fun getSessions(){
        sessionsUseCase.invoke().onEach {
            sessionsLiveData.value= it
        }.launchIn(viewModelScope)


    }


}