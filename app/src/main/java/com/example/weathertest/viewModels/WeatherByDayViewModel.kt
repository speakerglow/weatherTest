package com.example.weathertest.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathertest.network.NetProvider
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class WeatherByDayViewModel @Inject constructor(
    private val netProvider: NetProvider
) : ViewModel() {

    private val sharedFlow = MutableSharedFlow<String>()

    private var currentJob: Job? = null

    fun load() {
        currentJob = viewModelScope.launch {
            sharedFlow.emit("loading")
            try {
                sharedFlow.emit(netProvider.getForecast())
            } catch (ex: Exception) {
                sharedFlow.emit("unknownerror")
            }
        }
    }

    fun getFlow(): Flow<String> {
        return sharedFlow
    }

    fun clearJob() {
        currentJob?.cancel()
    }
}